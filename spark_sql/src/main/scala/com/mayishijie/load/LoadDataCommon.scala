package com.mayishijie.load

import com.mayishijie.common.Constants
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object LoadDataCommon {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("loadDataCommon").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")

    // 加载数据
    val df = spark.read.format(Constants.FORMAT_CSV).load(Constants.CSV_PATH)
    //df.createOrReplaceTempView("tempTable")
    //spark.sql("select * from tempTable").show()

    // 数据写出去
    df.coalesce(1)
    df.write.mode(SaveMode.Append).format(Constants.FORMAT_CSV).save(Constants.OUT_PATH)

  }

}
