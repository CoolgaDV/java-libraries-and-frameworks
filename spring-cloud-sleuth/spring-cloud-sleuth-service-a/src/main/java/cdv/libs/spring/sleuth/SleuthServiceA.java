package cdv.libs.spring.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Service that sends HTTP requests to service B
 *
 * @author Dmitry Kulga
 *         29.12.2018 20:43
 */
@SpringBootApplication
@EnableFeignClients(clients = ClientB.class)
public class SleuthServiceA {

    public static void main(String[] args) {
        SpringApplication.run(SleuthServiceA.class, args);
    }

}
