package cdv.libs.spring.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Cloud Admin client
 *
 * @author Dmitry Kulga
 * 30.12.2018 18:55
 */
@SpringBootApplication
public class AdminClient {

    public static void main(String[] args) {
        SpringApplication.run(AdminClient.class, args);
    }

}
