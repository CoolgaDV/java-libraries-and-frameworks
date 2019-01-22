package cdv.libs.kafka.clients.producer;

public class KafkaProducerApplication {

    public static void main(String[] args) throws InterruptedException {
        new SampleKafkaProducer("test.topic", 10).writeRecords();
    }

}
