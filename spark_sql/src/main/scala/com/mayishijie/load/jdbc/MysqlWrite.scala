package com.mayishijie.load.jdbc

import com.mayishijie.common.Constants
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object MysqlWrite {
  // 避免数据导入中文乱码
  val encodeUrl = "?useUnicode=true&characterEncoding=UTF-8";

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("mysqlWrite").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._

    val rdd = spark.sparkContext.makeRDD(Seq(Dept(3, "研发一部", "10002", 1), Dept(4, "研发一部", "10003", 1), Dept(5, "研发二部", "10004", 1)))

    val ds = rdd.toDS()

    ds.write.format("jdbc")
      .option(Constants.url_key,Constants.url+encodeUrl)
      .option(Constants.user_key,Constants.user)
      .option(Constants.password_key,Constants.password)
      .option(Constants.driver_key,Constants.driver)
      .option(Constants.dbtable_key,Constants.dbtable)
      .mode(SaveMode.Append)
      .save()

      //spark释放资源
      spark.stop()


  }
}

case class Dept(dept_id:Int,dept_name:String,dir_staff_seq_id:String,dept_num:Int)