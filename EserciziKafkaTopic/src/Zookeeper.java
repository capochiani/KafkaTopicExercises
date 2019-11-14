import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class Zookeeper {

	private static final Logger log = LoggerFactory.getLogger(Zookeeper.class);

	private static final String DEFAULT_ZK_CONNECT = "127.0.0.1:2181";

	private final Properties effectiveConfig;
	private final File logDir;
	private final TemporaryFolder tmpFolder;
	private final KafkaServer kafka;

	public Zookeeper(final Properties config) throws IOException {
		tmpFolder = new TemporaryFolder();
		tmpFolder.create();
		logDir = tmpFolder.newFolder();
		effectiveConfig = effectiveConfigFrom(config);
		final boolean loggingEnabled = true;

		final KafkaConfig kafkaConfig = new KafkaConfig(effectiveConfig, loggingEnabled);
		log.debug("Start Kafka broker (con log.dirs={} e Zookeeper{}) ...", logDir, zookeeperConnect());
		kafka = TestUtils.createServer(kafkaConfig, Time.SYSTEM);
		log.debug("Startup Kafka broker a {} e Zookeeper: {}) ...", brokerList(), zookeeperConnect());
	}

	private Properties effectiveConfigFrom(final Properties initialConfig) throws IOException {
		final Properties effectiveConfig = new Properties();
		effectiveConfig.put(KafkaConfig$.MODULE$.BrokerIdProp(), 0);
		effectiveConfig.put(KafkaConfig$.MODULE$.HostNameProp(), "127.0.0.1");
		effectiveConfig.put(KafkaConfig$.MODULE$.PortProp(), "9092");
		effectiveConfig.put(KafkaConfig$.MODULE$.NumPartitionsProp(), 1);
		// effectiveConfig.put(KafkaConfig$.MODULE$.AutoCreateTopicsEnableProp(), true);
		effectiveConfig.put(KafkaConfig$.MODULE$.MessageMaxBytesProp(), 1000000);
		effectiveConfig.put(KafkaConfig$.MODULE$.ControlledShutdownEnableProp(), true);

		effectiveConfig.putAll(initialConfig);
		effectiveConfig.setProperty(KafkaConfig$.MODULE$.LogDirProp(), logDir.getAbsolutePath());
		return effectiveConfig;
	}

	public String zookeeperConnect() {
		return effectiveConfig.getProperty("zookeeper.connect", DEFAULT_ZK_CONNECT);
	}
}
