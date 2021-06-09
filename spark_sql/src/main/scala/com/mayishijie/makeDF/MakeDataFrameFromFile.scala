package com.mayishijie.makeDF

import com.mayishijie.common.Constants
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession



object MakeDataFrameFromFile {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("makeDataFrameFromFile").setMaster("local")
    val spark = SparkSession.builder().config(conf).getOrCreate()

    // 通过文件创建,文件的每一行数据都必须是json格式,关注每一行是json,这里也需要注意spark sql的版本需要和spark core一致,不然会导致运行类型检查错误
    val df = spark.read.json(Constants.JSON_PATH)
    // 默认展示20行,可以指定具体行数
    df.show(100)
    //println("==============")
    //df.show(100)

  }
}
