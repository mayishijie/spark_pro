import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object TrancformDs {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("tranceFormDs").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(3))

    val ds = ssc.socketTextStream("localhost", 9999)

    // 无状态转换,利用transform算子,可以充分扩展ds api,利用rdd api
    ds.transform(
      rdd=>{
        val rd = rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
        val v = rd.sortBy(_._2,false)
        v
      }
    ).print()

    ssc.start()

    ssc.awaitTermination()
  }
}
