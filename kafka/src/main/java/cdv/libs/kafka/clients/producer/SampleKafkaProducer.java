package cdv.libs.kafka.clients.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class SampleKafkaProducer {

    private final String topic;
    private final int messagesCount;
    private final int delay;

    public void publish() throws InterruptedException {

        try (Producer<String, String> producer = new KafkaProducer<>(createBasicProperties())) {

            CountDownLatch latch = new CountDownLatch(messagesCount);
            sendMessages(producer, latch);
            latch.await(10, TimeUnit.SECONDS);

            System.out.println("messages are successfully sent");
        }
    }

    void publishInTransaction() throws InterruptedException {

        Properties properties = createBasicProperties();
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "test.transaction");
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            producer.initTransactions();
            try {

                CountDownLatch latch = new CountDownLatch(messagesCount);
                producer.beginTransaction();

                sendMessages(producer, latch);

                latch.await(10, TimeUnit.SECONDS);
                producer.commitTransaction();

                System.out.println("messages are successfully sent");

            } catch (KafkaException e) {
                System.out.println("failure: " + e);
                producer.abortTransaction();
            }
        }
    }


    private Properties createBasicProperties() {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return properties;
    }

    private void sendMessages(Producer<String, String> producer, CountDownLatch latch) {
        IntStream.range(0, messagesCount).forEach(value -> {

            ProducerRecord<String, String> record = new ProducerRecord<>(
                    topic,
                    "key_" + value,
                    "some" + value + " value" + value);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            producer.send(record, (metadata, e) -> {
                latch.countDown();
                System.out.println("topic: " + metadata.topic() + ", " +
                        "offset: " + metadata.offset() + ", " +
                        "partition: " + metadata.partition() + ", " +
                        "exception: " + e);
            });
        });
    }

}
