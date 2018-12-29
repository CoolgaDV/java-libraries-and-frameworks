package cdv.libs.spring.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Service that receives HTTP requests from service A
 *
 * @author Dmitry Kulga
 *         29.12.2018 21:08
 */
@SpringBootApplication
public class SleuthServiceB {

    public static void main(String[] args) {
        SpringApplication.run(SleuthServiceB.class, args);
    }

}
