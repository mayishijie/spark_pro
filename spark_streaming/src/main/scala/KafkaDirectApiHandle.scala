import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KafkaDirectApiHandle {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("handleKafka").setMaster("local[2]")

    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
    val kafkaParams = Map[String,String](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "mayi101:9092,mayi102:9092,mayi103:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "stream-kafka08"
    )

    // 偏移量的获取,精准一次性消费,可以将偏移量,主题,分区保存在MySQL等事务性数据库中
    def fromOffSets():Map[TopicAndPartition,Long] = {
      Map[TopicAndPartition,Long](
        TopicAndPartition("kafka-receive",0)->30L,
        TopicAndPartition("kafka-receive",1)->32L
      )
    }

    //def messageHandler():MessageAndMetadata[String,String]={
      //val metadata = new  MessageAndMetadata().message()
      //metadata
    //}

    val kafkaStreaming = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder,String](
      ssc,
      kafkaParams,
      fromOffSets,
      (m: MessageAndMetadata[String, String]) => {
        //println(m.message())
        m.message()
      }
    )

    kafkaStreaming.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    var offsetRanges = Array.empty[OffsetRange]

    // 手动维护偏移量
    kafkaStreaming.transform{
      rdd=>{
        offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
        rdd
      }
    }.foreachRDD { rdd =>
      for (o <- offsetRanges) {
        println(s"${o.topic} ${o.partition} ${o.fromOffset} ${o.untilOffset}")
      }
    }

    ssc.start()
    ssc.awaitTermination()
  }
}
