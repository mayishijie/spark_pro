package com.mayishijie.makeDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession



object MakeDataFrameFromFile {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("makeDataFrameFromFile").setMaster("local")
    val spark = SparkSession.builder().config(conf).getOrCreate() //隐式转换
//    val sc = spark.sparkContext
    val df = spark.read.json("/Users/tty/IdeaProjects/spark/spark_pro/spark_sql/src/main/resources/test.json")
    df.show()
    Thread.sleep(10000000)
//    sc.stop()
  }
}
