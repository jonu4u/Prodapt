import Dependencies._
import sbt.Keys._

ThisBuild / scalaVersion := scalaV
ThisBuild / organization := "prodapt"
ThisBuild / version      := "0.1"
ThisBuild / dependencyOverrides ++= depsOverrides
ThisBuild / libraryDependencies ++= (sparkDependencies ++ other_deps).map(excludeLog4j)
ThisBuild / parallelExecution in Test := false
ThisBuild / publishMavenStyle := true
ThisBuild / publishTo := Some(Resolver.file("jars",file("jars")))
ThisBuild / logBuffered := false


lazy val root = project
  .in(file("."))
  .settings(
    name := "ETL_Prodapt",
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:+CMSClassUnloadingEnabled"),
    publishArtifact := false,
    fork in Test := true
  )
  .aggregate(
    `batch-events`  
  )

lazy val `batch-events` = project
  .in(file("batch-events"))
