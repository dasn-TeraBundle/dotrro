%KAFKA_HOME%\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --topic patients --partitions 1 --replication-factor 1 --config segment.bytes=1000000