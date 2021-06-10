package com.mayishijie.udf

import com.mayishijie.common.Constants
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object UdfFunction {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("udfFuntion").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()

    val df = spark.read.json(Constants.JSON_PATH)
    df.createTempView("person");

    val addTopic = spark.udf.register("addTopic", (x: String) => x + "_topic")

    spark.sql("select addTopic(name) as name,age from person").show(2)
  }
}
