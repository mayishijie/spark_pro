package com.mayishijie.sparkHive

import com.mayishijie.common.Constants
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkHive {
  def main(args: Array[String]): Unit = {
    // 配置用户名和密码,有Hadoop的权限
    Constants.setSystemConfig()

    val conf = new SparkConf().setAppName("sparkHive").setMaster("local[2]")
    //conf.set("HADOOP")
    val spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate()

    //spark.sql("use mayi")
    //spark.sql(
    //  "show tables"
    //).show()
    spark.sql("select * from mayi.business ").show()
    spark.stop()
  }
}
