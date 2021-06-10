package com.mayishijie.load.jdbc

import com.mayishijie.common.Constants
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.util.Properties

object MysqlRead {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("mysqlDemo").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()

    //方式一
    //method1(spark)

    //方式二
    method2(spark)

    //方式三
    //method3(spark)


  }
  /**方式一,单个配置*/
  def method1(spark:SparkSession): Unit ={
    spark.read.format("jdbc")
      .option(Constants.url_key, Constants.url)
      .option(Constants.driver_key, Constants.driver)
      .option(Constants.user_key,Constants.user)
      .option(Constants.password_key,Constants.password)
      .option(Constants.dbtable_key,Constants.dbtable)
      .load().show()
  }

  /**通过配置properties文件*/
  def method2(spark:SparkSession): Unit = {
    val properties = new Properties()
    properties.put(Constants.user_key, Constants.user)
    properties.put(Constants.password_key,Constants.password)
    properties.put(Constants.driver_key,Constants.driver)

    spark.read
      .jdbc(Constants.url,Constants.dbtable,properties)
      .show()

  }

  /**map参数*/
  def method3(spark:SparkSession): Unit ={
    spark.read.format("jdbc")
      .options(Map(Constants.url_key->(Constants.url+"?"+Constants.user_key+"="+Constants.user+"&"+Constants.password_key+"="+Constants.password),
        Constants.dbtable_key->Constants.dbtable,Constants.driver_key->Constants.driver))
      .load().show()
  }

}
