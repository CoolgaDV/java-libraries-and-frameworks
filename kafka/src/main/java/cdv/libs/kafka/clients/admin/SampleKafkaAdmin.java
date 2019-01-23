package cdv.libs.kafka.clients.admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

public class SampleKafkaAdmin {

    public void createTopic(String topicName) {

        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        NewTopic topic = new NewTopic(topicName, 1, (short) 1).configs(new HashMap<>());

        try (AdminClient adminClient = AdminClient.create(config)) {
            adminClient.createTopics(Collections.singletonList(topic));
        }
    }

}
