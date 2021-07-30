# First steps with kafka

This project will show how to start with kafka with any framework.

Lets go!

First we need to download kafka and zookeeper

The Kafka needing zookeeper to store some information  

http://kafka.apache.org/

https://zookeeper.apache.org/

After that, we can begin initializing zookeeper with the following command.

``` 
  bin/zkServer.sh start conf/zoo.cfg 
```
and also can be starting kafka

``` 
    bin/kafka-server-start.sh config/server.properties
```

List topics
``` 
    bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe
```

Alter topics
```
    bin/kafka-topics.sh --alter --zookeeper localhost:2181 --topic TOPIC --partitions 3
```

Details consumer
``` 
    bin/kafka-consumer-froups.sh --all-groups --bootstrap-server localhost:9092 --describe
```
Obs: For the distribution of messages partition to happen, it will be necessary to send a unique key.

 









