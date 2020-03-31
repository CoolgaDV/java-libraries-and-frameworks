package cdv.libs.spring.cloud.vault;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LifeCycleListener {

    @Value("${properties.securedProperty}")
    private String securedProperty;
    @Value("${properties.unsecuredProperty}")
    private String unsecuredProperty;

    @EventListener(ContextRefreshedEvent.class)
    public void handleEvent() {
        log.info("Secured property: {}", securedProperty);
        log.info("Unsecured property: {}", unsecuredProperty);
    }

}
