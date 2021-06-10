package com.mayishijie.makeDF

import com.mayishijie.common.Constants
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object DfToDs {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("DfToDs").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()

    val df = spark.read.json(Constants.JSON_PATH)

    import spark.implicits._

    val ds = df.as[Person]
    ds.show()
  }
}

//case class Person(name:String,age:String)
