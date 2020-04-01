package cdv.libs.spring.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final List<Integer> values = new ArrayList<>();

    private final MeterRegistry meterRegistry;

    private Counter valuesCounter;
    private Timer valuesTimer;

    @PostConstruct
    public void initGauge() {
        Gauge.builder("custom_values_gauge", () ->
                values.stream()
                        .mapToInt(Integer::intValue)
                        .sum()
        ).register(meterRegistry);
        valuesCounter = meterRegistry.counter("custom_values_counter");
        valuesTimer = meterRegistry.timer("custom_values_timer");
    }

    @GetMapping("/values")
    public String getValues() {
        return valuesTimer.record(() -> {
            valuesCounter.count();
            values.add(ThreadLocalRandom.current().nextInt(-100, 100));
            return values.toString();
        });
    }

}
