import java.util.Properties;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DeleteTopic {
	// Delete a Kafka topic called "pippo".

	public void deleteTopic(final String pippo) {

		log.debug("Cancella il topic {}", pippo);
		final Properties properties = new Properties();
		properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList());

		try (final AdminClient adminClient = AdminClient.create(properties)) {
			adminClient.deleteTopics(Collections.singleton(pippo)).all().get();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		} catch (final ExecutionException e) {
			if (!(e.getCause() instanceof UnknownTopicOrPartitionException)) {
				throw new RuntimeException(e);
			}
		}
	}
}
