package com.prodapt
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSessionUtils {

  def getSession(name: String): SparkSession = {
    val conf = new SparkConf()
    conf.set("spark.master", "local[*]")
    conf.set("spark.sql.session.timeZone", "UTC")
    val session = SparkSession.builder().config(conf).getOrCreate()
    session.sparkContext.setLogLevel("INFO")
    session
  }
  }