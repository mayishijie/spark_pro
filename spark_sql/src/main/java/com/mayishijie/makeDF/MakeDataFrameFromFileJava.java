package com.mayishijie.makeDF;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * @author tty
 * @version 1.0 2021-06-09 10:13
 */
public class MakeDataFrameFromFileJava {
    public static void main(String[] args) throws InterruptedException {
        SparkSession ss = SparkSession.builder().appName("makeDataFrameFromFileJava").master("local[2]").getOrCreate();
        Dataset<Row> ds = ss.read().json("/Users/tty/IdeaProjects/spark/spark_pro/spark_sql/src/main/resources/test.json");
        ds.show();
        Thread.sleep(1000000);
        //ss.stop();

    }
}
