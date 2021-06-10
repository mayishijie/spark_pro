package com.mayishijie.load.jdbc

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object MysqlWrite {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("mysqlWrite").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()

  }
}
