import sbt._

object Dependencies {

  val scalaV = "2.11.8"
  val sparkV = "2.4.3"
  val jacksonV = "2.9.5"

  val depsOverrides = Seq(
    "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonV,
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonV,
    "com.fasterxml.jackson.core" % "jackson-core" % jacksonV,
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonV,
    "commons-codec" % "commons-codec" % "1.10"
  )

  private val commonSparkDependencies = Seq(
    "org.apache.spark" %% "spark-core" % sparkV,
    "org.apache.spark" %% "spark-sql" % sparkV
  )

  val sparkDependencies = commonSparkDependencies.map(_ % Provided)
  
  val other_deps = Seq(
    "org.slf4j" % "slf4j-api" % "1.7.26",
    "org.slf4j" % "log4j-over-slf4j" % "1.7.26",  
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.apache.commons" % "commons-lang3" % "3.5" % "compile"
  )
  
  def excludeLog4j(moduleId: ModuleID): ModuleID =
    moduleId.excludeAll(
      ExclusionRule(organization = "log4j"),
      ExclusionRule(organization = "org.slf4j", name = "slf4j-log4j12")
    )

}
