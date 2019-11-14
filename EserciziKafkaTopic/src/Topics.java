import java.util.Collections;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Topics {


	// 1. Crea un Kafka topic con partition 1 e replication factor 2.
	
	public void esTopic1(final String pippo) {
		esTopic1(pippo, 1, (short) 2, Collections.emptyMap());
	}

	// 2. Crea un Kafka topic con parametri: topic, partition, replication.
	 
	public void esTopic2(final String pippo, final int partitions, final short replication) {
		esTopic2(pippo, partitions, replication, Collections.emptyMap());
	}

	// 3. Crea un Kafka topic con parametri: topic, partition, replication, topicConfig.
	
	public void esTopic3(final String pippo, final int partitions, final short replication,
			final Map<String, String> topicConfig) {
		Object log;
		log.debug("Crea topic come segue: { nome topic: {}, partitions: {}, replication: {}, config: {} }", topic,
				partitions, replication, topicConfig);

		final Properties properties = new Properties();
		properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList());

		try (final AdminClient adminClient = AdminClient.create(properties)) {
			final NewTopic newTopic = new NewTopic(pippo, partitions, replication);
			newTopic.configs(topicConfig);
			adminClient.createTopics(Collections.singleton(newTopic)).all().get();
		} catch (final InterruptedException | ExecutionException fatal) {
			throw new RuntimeException(fatal);
		}

	}
}
