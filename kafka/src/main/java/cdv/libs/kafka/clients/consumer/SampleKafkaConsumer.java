package cdv.libs.kafka.clients.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.IntStream;

import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@RequiredArgsConstructor
public class SampleKafkaConsumer {

    private final String topic;

    public void consume() {

        Properties properties = createBasicProperties();
        properties.put(GROUP_ID_CONFIG, "test-" + System.currentTimeMillis());
        properties.put(ENABLE_AUTO_COMMIT_CONFIG, true);
        properties.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(singletonList(topic));

        IntStream.range(0, 3).forEach(index -> {

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));

            for (ConsumerRecord<String, String> record : records) {

                System.out.println("offset: " + record.offset() + ", " +
                        "key: " + record.key() + ", " +
                        "value: " + record.value());
            }
        });
    }

    @SuppressWarnings("unused")
    public void consumeWithManualCommit() {

        Properties properties = createBasicProperties();
        properties.put(ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(GROUP_ID_CONFIG, "test-manual-commit");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(singletonList(topic));

        IntStream.range(0, 3).forEach(index -> {

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));

            for (ConsumerRecord<String, String> record : records) {

                System.out.println("offset: " + record.offset() + ", " +
                        "key: " + record.key() + ", " +
                        "value: " + record.value());

                Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
                offsets.put(
                        new TopicPartition(record.topic(), record.partition()),
                        new OffsetAndMetadata(record.offset() + 1, "no metadata"));
                consumer.commitSync(offsets);
            }
        });

    }

    private Properties createBasicProperties() {

        Properties properties = new Properties();

        properties.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(SESSION_TIMEOUT_MS_CONFIG, "6000");
        properties.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return properties;
    }

}
