package com.mayishijie.makeDF

import com.mayishijie.common.Constants
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object DfToRdd {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("dfToRdd").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()

    val df = spark.read.json(Constants.JSON_PATH)

    // df转化后的值就变成rdd[row]
    val dfToRdd = df.rdd

    dfToRdd.collect().foreach(println)
  }
}
