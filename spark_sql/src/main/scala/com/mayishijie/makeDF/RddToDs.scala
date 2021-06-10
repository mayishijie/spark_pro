package com.mayishijie.makeDF

import com.mayishijie.common.Constants
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object RddToDs {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("rddToDs").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    val rdd = sc.textFile(Constants.PERSON_CSV_PATH)
    val ds = rdd.map(
      line => {
        val filds = line.split(",")
        Person(filds(0), filds(1).toInt)
      }
    ).toDS

    ds.select("name","age").show(2)
  }
}


