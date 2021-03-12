package com.prodapt
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.spark.sql.functions._

class Assignment(val input:String,val output:String,val session: SparkSession) {

  def run(): Unit={
import session.implicits._
    val inputDf=session.readStream.format("text")
  .load(input)

    val filtered=inputDf.filter($"value".contains("GET      http://omwssu"))
      .withColumn("url",regexp_extract($"value","(http:\\/\\/\\S+)",1))
      .withColumn("cpe_id",regexp_extract($"url","((http:\\/\\/\\w+.\\w+.\\w+.\\w+.\\w+\\/)\\w+).(\\w+.\\w+.\\w+).(\\w+).(\\w\\/\\w)",3))
      .withColumn("fqdn",regexp_extract($"url","((http:\\/\\/\\w+.\\w+.\\w+.\\w+.\\w+\\/)\\w+).(\\w+.\\w+.\\w+).(\\w+).(\\w\\/\\w)",1))
      .withColumn("message",$"url")
      .withColumn("action",regexp_extract($"url","((http:\\/\\/\\w+.\\w+.\\w+.\\w+.\\w+\\/)\\w+).(\\w+.\\w+.\\w+).(\\w+).(\\w\\/\\w)",4))
      .withColumn("erro_code",regexp_extract($"url","((http:\\/\\/\\w+.\\w+.\\w+.\\w+.\\w+\\/)\\w+).(\\w+.\\w+.\\w+).(\\w+).(\\w\\/\\w)",5))
      .withColumn("timestamp",regexp_extract($"value","([0-9]{4}-[0-9]{2}-[0-9]{2}\\s+[0-9]{2}:[0-9]{2}:[0-9]{2})",1))
      .withColumn("timestamp",regexp_replace($"timestamp","\\s+","T"))
      .withColumn("erro_code",regexp_replace($"erro_code","[^0-9]","."))
      .drop("value","url")

    val query=filtered.writeStream
      .format("csv")
      .option("truncate","false")
    .option("checkpointLocation", output+"/checkpoint")
      .option("path",output)
      .outputMode(OutputMode.Append()).trigger(Trigger.Once())
      .start()
      .awaitTermination()

  }

}
object Assignment{
  def main(args: Array[String]): Unit = {
    val assignment=new Assignment(args(0),args(1), SparkSessionUtils.getSession("Prodapt-Job"))
    assignment.run()

  }
}
