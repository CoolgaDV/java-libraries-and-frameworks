package cdv.libs.kafka.clients.admin;

public class KafkaAdminApplication {

    public static void main(String[] args) {
        new SampleKafkaAdmin().createTopic("sample.topic");
    }

}
