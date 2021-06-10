package com.mayishijie.makeDF

import com.mayishijie.common.Constants
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RddToDF {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("toDf").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    val sc = spark.sparkContext

    val rdd = sc.textFile(Constants.PERSON_CSV_PATH)

    //手动转换DF
    //convert2Df(spark,rdd)

    // 通过样例类,生产常用
    conver2DfByClass(spark,rdd)

  }

  /**手动转换DF*/
  def convert2Df(spark:SparkSession,rdd:RDD[String]):Unit={
    //导入的不是spark手动包
    import spark.implicits._
    rdd.map(line=>{
      val filds = line.split(",")
      (filds(0),filds(1).trim.toInt)
    }).toDF().show()
  }

  /**通过样例类来转化*/
  def conver2DfByClass(spark:SparkSession,rdd: RDD[String]):Unit={
    import spark.implicits._
    rdd.map(
      line=>{
        val filds = line.split(",")
        Person(filds(0),filds(1).trim.toInt)
      }
    ).toDF.show()
  }

}

case class Person(name:String,age:Long)
