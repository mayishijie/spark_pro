package com.mayishijie.common

object Constants {

  // 文件路径
  val COMMON_PATH = "/Users/tty/IdeaProjects/spark/spark_pro/spark_sql/src/main/resources/"
  val JSON_PATH = COMMON_PATH + "test.json"
  val PERSON_CSV_PATH = COMMON_PATH+"person.txt"


  // 文件格式
  val FORMAT_JSON = "json"
  val FORMAT_CSV = "csv"


  def sleep():Unit={
    Thread.sleep(1000000)
  }

}
