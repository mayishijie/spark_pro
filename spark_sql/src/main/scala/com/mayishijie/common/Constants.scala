package com.mayishijie.common

object Constants {

  // 文件路径
  val COMMON_PATH = "/Users/tty/IdeaProjects/spark/spark_pro/spark_sql/src/main/resources/"
  val JSON_PATH: String = COMMON_PATH + "test.json"
  val PERSON_CSV_PATH: String = COMMON_PATH + "person.txt"
  val CSV_PATH: String = COMMON_PATH + "person.csv"


  // 输出目录
  val OUT_PATH: String = COMMON_PATH + "output"

  // 文件格式
  val FORMAT_JSON = "json"
  val FORMAT_CSV = "csv"

  //数据库配置
  val url_key = "url"
  val user_key = "user"
  val password_key = "password"
  val driver_key = "driver"
  val dbtable_key = "dbtable"


  val url = "jdbc:mysql://mayi110:3306/mayi"
  val user = "root"
  val password = "root"
  val driver = "com.mysql.jdbc.Driver"
  val dbtable = "dept"





  def sleep():Unit={
    Thread.sleep(1000000)
  }


  def setSystemConfig():Unit={
    System.setProperty("HADOOP_USER_NAME","mayi")
    System.setProperty("HADOOP_USER_PASSWORD","mayi")

  }

}
