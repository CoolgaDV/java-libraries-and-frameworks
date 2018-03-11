package cdv.libs.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Spring Cloud Config Server application.
 *
 * The only thing we need to do to setup config server is
 * to use annotation {@link EnableConfigServer}
 *
 * @author Dmitry Kulga
 *         11.03.2018 18:14
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServer {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class, args);
    }

}
