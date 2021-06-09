package com.mayishijie.value

import org.apache.spark.{SparkConf, SparkContext}

object MapRdd {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("map").setMaster("local")
    val sc = new SparkContext(conf)

    val mapRdd = sc.makeRDD(List(1,2,3))

    mapRdd.map(_ * 2).collect().foreach(println)


    Thread.sleep(1000000)
    sc.stop()
  }
}
