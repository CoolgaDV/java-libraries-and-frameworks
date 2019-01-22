package cdv.libs.kafka.clients.streams;

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

        Properties properties = new Properties();
        properties.put(APPLICATION_ID_CONFIG, "wordcount-live-test");
        properties.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        String inputTopic = "streams.input.topic.4";
        String outputTopic = "streams.output.topic.4";

        new SampleKafkaProducer(inputTopic, 5).writeRecords();

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
            new SampleKafkaConsumer(outputTopic).readRecords();
        } finally {
            streams.close();
        }
    }

}
