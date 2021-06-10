# 1. Rdd,DataFrame与DataSet之间的关系
## 1. 三者共性
> 1. 都是spark平台下的分布式弹性数据集
> 2. 都是惰性机制,只有触发action算子,才会真正执行
> 3. 三者都有很多共同的函数,filter,排序等
> 4. 转DF,DS都需要import spark.implicits._（在创建好SparkSession对象后尽量直接导入）
> 5. 都会根据内存情况自动缓存运算
> 6. 三者都有partition的概念
> 7. DF与DS都可以通过模式匹配获取各个字段的值和类型
## 2. 三者区别
### 1. RDD
> 1. RDD一般和Spark MLib一起使用
> 2. RDD不支持SaprkSql操作
### 2. DF

### 3. DS

## 3. 三者相互转化
> 1. 所有需要转DF的,都必须调用:toDF()
> 2. 所有需要转RDD的,都必须直接调用:.rdd()
> 3. 转DS:
>   1. 样例类,RDD->DS的转化: toDS()
>   2. DF->DS的转化: as[类型]