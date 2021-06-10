package com.mayishijie.udaf

import com.mayishijie.common.Constants
import com.mayishijie.makeDF.Person
import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SparkSession}

object TestAgeAvg {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("myAvg").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._
    val ds = spark.read.textFile(Constants.PERSON_CSV_PATH)
    val dsValue = ds.map(
      line => {
        val filds = line.split(",")
        Person(filds(0), filds(1).trim.toLong)
      }
    )

    //创建聚合函数
    val myAverage = new MyAvgUdtfFunction

  //  //在spark中注册聚合函数
  //  spark.udf.register("avgAge",myAverage)
  //
  //  //读取数据  {"username": "zhangsan","age": 20}
  //  val df: DataFrame = spark.read.json("D:\\dev\\workspace\\spark-bak\\spark-bak-00\\input\\test.json")
  //
  //  //创建临时视图
  //  df.createOrReplaceTempView("user")
  //
  //  //使用自定义函数查询
  //  spark.sql("select avgAge(age) from user").show()
  //}

    spark.udf.register("myAverage",myAverage)
    dsValue.createOrReplaceTempView("person")
    spark.sql("select name, myAverage(age) from person group by name").show()
    //spark.sql("select age from person ").show()

    Constants.sleep()
  }
}

class MyAvgUdtfFunction extends UserDefinedAggregateFunction{
  // 聚合函数输入参数的数据类型
  override def inputSchema: StructType = StructType(Array(StructField("age",LongType)))

  // 聚合函数缓存区中数据类型
  override def bufferSchema: StructType = StructType(Array(StructField("sum",LongType),StructField("count",LongType)))

  // 函数返回值类型
  override def dataType: DataType = DoubleType

  // 稳定性：对于相同的输入是否一直返回相同的输出。
  override def deterministic: Boolean = true

  // 函数缓冲区初始化
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    // 存年龄缓冲
    buffer(0) = 0L
    // 存年龄个数
    buffer(1) = 0L
  }


  //更新缓冲区数据
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    if (!input.isNullAt(0)) {
      buffer(0) = buffer.getLong(0) + input.getLong(0)
      buffer(1) = buffer.getLong(1) + 1
    }
  }

  //合并缓存区数据
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getLong(0)+buffer2.getLong(0)
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  // 计算结果
  override def evaluate(buffer: Row): Any = {
     buffer.getLong(0).toDouble/buffer.getLong(1)
  }
}
