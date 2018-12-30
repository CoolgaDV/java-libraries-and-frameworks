package cdv.libs.spring.admin;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Cloud Admin server
 *
 * @author Dmitry Kulga
 *         30.12.2018 18:29
 */
@SpringBootApplication
@EnableAdminServer
public class AdminServer {

    public static void main(String[] args) {
        SpringApplication.run(AdminServer.class, args);
    }

}
