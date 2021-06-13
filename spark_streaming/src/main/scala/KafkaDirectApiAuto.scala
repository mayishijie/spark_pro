import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KafkaDirectApiAuto {
  def main(args: Array[String]): Unit = {
    val CHECK_POIN_PATH = "/Users/tty/IdeaProjects/spark/spark_pro/spark_streaming/src/main/resources/cp"
    val conf = new SparkConf().setAppName("kafkaDirect").setMaster("local[2]")

    //方式一
    //虽然设置了检查点,但是因为创建的StreamingContext对象,没有设置获取检查点信息,所以会存在丢失数据的情况,在程序启动之前,如果kafka还在生产消息
    //那么这个时候的消息,在程序kafka消费端程序启动后,因为没有从检查点拿数据,自然是不会消费这期间新产生的消息的
    //val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

    //方式二
    //弥补了方式一的缺陷,但是在该版本中,缺点依然存在
    //    自定的维护偏移量，偏移量维护在checkpiont中
    //    修改StreamingContext对象的获取方式，先从检查点获取，如果检查点没有，通过函数创建。会保证数据不丢失
    //缺点
    //    1. 会产生很多小文件,每个时间周期中,都会产生很多检查点小文件(方式一也一样)
    //     2. 启动后,会将程序记录的最开始的偏移量时间开始到现在,按照周期会从新执行一遍进行消费,如果程序和最开始记录时间过长,会有在一个时间点有很大的性能问题
    val ssc = StreamingContext.getActiveOrCreate(CHECK_POIN_PATH, () => {
      new StreamingContext(conf, Seconds(5))
    })

    ssc.checkpoint(CHECK_POIN_PATH);

    val cds = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](
      ssc,
      Map(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "mayi101:9092,mayi102:9092,mayi103:9092",
        ConsumerConfig.GROUP_ID_CONFIG -> "stream-kafka08"
      ),
      Set("kafka-receive")
    )

    cds.flatMap(_._2.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
