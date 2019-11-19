# Kafka-Topic Exercise

Il topic è uno dei parametri più vulnerabili nell'Architettura Kafka.
Attraverso implementazioni base, si cerca di analizzarne i possibili interventi di sviluppo.


Create Topic (sh/bat):

> bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
> bin/kafka-topics.sh --list --bootstrap-server localhost:9092

Send Topic: 
> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
Start Consumer: 
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
