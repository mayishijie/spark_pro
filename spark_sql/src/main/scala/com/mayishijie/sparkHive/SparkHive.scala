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
    //spark.sql("select name,orderdate,cost,rank() over(partition by name order by cost desc) as r from mayi.business ").show()


    //建表
    //createTable(spark)

    // 加载数据
    //loadData(spark)

    //查询
    queryData(spark)

    spark.stop()
  }

  /**建表*/
  def createTable(spark:SparkSession): Unit ={
    //spark.sql("use mayi")
    spark.sql(
      """
        |CREATE TABLE mayi.`user_visit_action`(
        |  `date` string,
        |  `user_id` bigint,
        |  `session_id` string,
        |  `page_id` bigint,
        |  `action_time` string,
        |  `search_keyword` string,
        |  `click_category_id` bigint,
        |  `click_product_id` bigint,
        |  `order_category_ids` string,
        |  `order_product_ids` string,
        |  `pay_category_ids` string,
        |  `pay_product_ids` string,
        |  `city_id` bigint)
        |row format delimited fields terminated by '\t'
        |""".stripMargin)

    spark.sql(
      """
        |CREATE TABLE mayi.`product_info`(
        |  `product_id` bigint,
        |  `product_name` string,
        |  `extend_info` string)
        |row format delimited fields terminated by '\t'
        |""".stripMargin)

    spark.sql(
      """
        |CREATE TABLE mayi.`city_info`(
        |  `city_id` bigint,
        |  `city_name` string,
        |  `area` string)
        |row format delimited fields terminated by '\t'
        |""".stripMargin)
  }

  /**导入数据*/
  def loadData(spark:SparkSession): Unit ={
    spark.sql(
      """
        |load data local inpath
        |'/Users/tty/IdeaProjects/spark/spark_pro/spark_sql/src/main/resources/sql-data/city_info.txt'
        |into table mayi.city_info
        |""".stripMargin)

    spark.sql(
      """
        |load data local inpath
        |'/Users/tty/IdeaProjects/spark/spark_pro/spark_sql/src/main/resources/sql-data/product_info.txt'
        |into table mayi.product_info
        |""".stripMargin)

    spark.sql(
      """
        |load data local inpath
        |'/Users/tty/IdeaProjects/spark/spark_pro/spark_sql/src/main/resources/sql-data/user_visit_action.txt'
        |into table mayi.user_visit_action
        |""".stripMargin)
  }

  // 查询数据
  def queryData(spark:SparkSession): Unit ={
    spark.sql("select * from mayi.city_info").show()
    spark.sql("select * from mayi.product_info").show()
    spark.sql("select * from mayi.user_visit_action").show()
  }

}
