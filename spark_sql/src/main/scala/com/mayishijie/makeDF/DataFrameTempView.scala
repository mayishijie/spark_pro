package com.mayishijie.makeDF

import com.mayishijie.common.Constants
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object DataFrameTempView {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("tempView").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()

    val df = spark.read.format(Constants.FORMAT_JSON).json(Constants.JSON_PATH)

    //spark Sql风格
    //createTempView(spark, df)

    // dsl风格创建
    styleForDSL(df)

  }

  /**临时视图创建并执行查询*/
  def createTempView(spark:SparkSession,df:DataFrame):Unit={
    //对于spark.sql风格的,必须要创建临时视图,否则无法执行查询
    // 创建临时视图
    df.createTempView("people")

    // 执行查询,需要spark session 对象,利用spark.sql风格执行查询
    spark.sql("select * from people where age between 108 and 110").show()
  }

  /**DSL风格查询,无需创建临时视图,可以直接使用*/
  def styleForDSL(df:DataFrame):Unit={
    //df.select("name","age").show() //指定列查询

    //df.groupBy("age").count().show()
  }



}


