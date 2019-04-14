package cdv.lib.spring.data.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class DataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class);
    }

    @Bean
    public CommandLineRunner runSample(ItemRepository repository) {
        return (args) -> {

            repository.save(new Item().setName("First").setKind("A"));
            repository.save(new Item().setName("Second").setKind("A"));
            repository.save(new Item().setName("Third").setKind("B"));

            log.info("Find all: {}", repository.findAll());
            log.info("Find second: {}", repository.findOne(2L));
            log.info("Find by name: {}", repository.findByName("Third"));
            log.info("Find by kind: {}", repository.findByKind("A"));
        };
    }

}
