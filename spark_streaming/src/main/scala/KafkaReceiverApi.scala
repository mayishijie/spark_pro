import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 1. 查看当前有的主题
 * kafka-topics.sh --list --bootstrap-server mayi101:9092
 *
 * 2. 在服务器上创建对应的主题
 * kafka-topics.sh --create --bootstrap-server mayi101:9092 --topic kafka-receive --partitions 2 --replication-factor 2
 *
 * 3. 生产消息,kafka预备消费
 * kafka-console-producer.sh --broker-list mayi101:9092 --topic kafka-receive
 */
object KafkaReceiverApi {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("kafkaRece").setMaster("local")
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

    //KafkaUtils.
    val kafkaDs = KafkaUtils.createStream(
      ssc,
      "mayi101:2181,mayi102:2182,mayi103:2181",
      "stream-kafka08",
      Map("kafka-receive" -> 2)
    )

    val ds = kafkaDs.map(_._2).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
    ds.print()

    ssc.start()

    ssc.awaitTermination()
  }
}
