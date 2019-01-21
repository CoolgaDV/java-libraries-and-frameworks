package cdv.libs.kafka.clients.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class KafkaProducerApplication {

    private static final int MESSAGES_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);

        try(Producer<String, String> producer = new KafkaProducer<>(properties)) {

            CountDownLatch latch = new CountDownLatch(MESSAGES_COUNT);

            IntStream.range(0, MESSAGES_COUNT).forEach(value -> {

                ProducerRecord<String, String> record = new ProducerRecord<>(
                        "test.topic",
                        "key_" + value,
                        "value_" + value);

                producer.send(record, (metadata, e) -> {
                    latch.countDown();
                    System.out.println("offset: " + metadata.offset() + ", " +
                            "partition: " + metadata.partition() + ", " +
                            "exception: " + e);
                });
            });

            latch.await(10, TimeUnit.SECONDS);

            System.out.println("messages are successfully sent");
        }
    }

}
