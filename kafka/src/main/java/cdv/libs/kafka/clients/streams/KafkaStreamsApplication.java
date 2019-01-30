package cdv.libs.kafka.clients.streams;

import cdv.libs.kafka.clients.admin.SampleKafkaAdmin;
import cdv.libs.kafka.clients.consumer.SampleKafkaConsumer;
import cdv.libs.kafka.clients.producer.SampleKafkaProducer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

import static org.apache.kafka.streams.StreamsConfig.*;

public class KafkaStreamsApplication {

    private static final Pattern SPLITTER = Pattern.compile("\\W+");

    public static void main(String[] args) throws InterruptedException {

        String inputTopic = "streams.input.topic";
        String outputTopic = "streams.output.topic";

        new SampleKafkaAdmin().createTopic(inputTopic);

        Properties properties = new Properties();
        properties.put(APPLICATION_ID_CONFIG, "wordcount-live-test");
        properties.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        streamsBuilder
                .<String, String>stream(inputTopic)
                .peek(((key, value) -> System.out.println("key: " + key + ", value: " + value)))
                .flatMapValues(value -> Arrays.asList(SPLITTER.split(value)))
                .mapValues((ValueMapper<String, String>) String::toUpperCase)
                .to(outputTopic);

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), properties);
        try {
            streams.start();
            new SampleKafkaProducer(inputTopic, 5, 0).publish();
            new SampleKafkaConsumer(outputTopic).consume();
        } finally {
            streams.close();
        }
    }

}
