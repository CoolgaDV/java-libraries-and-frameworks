package cdv.libs.kafka.clients.consumer;

public class KafkaConsumerApplication {

    public static void main(String[] args) {
        new SampleKafkaConsumer("test.topic").consume();
    }

}
