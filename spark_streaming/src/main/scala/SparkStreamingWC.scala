import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingWC {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("sswc").setMaster("local[2]")
    val ssc = new StreamingContext(conf,Seconds(3))

    val socketDs = ssc.socketTextStream("mayi101", 9999)

    val ds = socketDs.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_)

    ds.print()
    ssc.start()

    ssc.awaitTermination()


  }
}
