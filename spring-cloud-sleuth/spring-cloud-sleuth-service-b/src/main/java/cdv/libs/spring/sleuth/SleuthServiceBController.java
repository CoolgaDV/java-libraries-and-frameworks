package cdv.libs.spring.sleuth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that accepts HTTP request and prints Sleuth HTTP headers to log
 *
 * @author Dmitry Kulga
 *         29.12.2018 21:14
 */
@Slf4j
@RestController
public class SleuthServiceBController {

    @GetMapping("service/b")
    public void call(
            @RequestHeader("x-b3-parentspanid") String parentSpan,
            @RequestHeader("x-b3-traceid") String traceId,
            @RequestHeader("x-span-name") String spanName,
            @RequestHeader("x-b3-spanid") String spanId,
            @RequestHeader("x-b3-sampled") String samplingFlag,
            @RequestHeader("b3") String compositeId
    ) {
        log.info("Service B is called:\n" +
                "Parent span ID: {}\n" +
                "Trace ID: {}\n" +
                "Span name: {}\n" +
                "Span ID: {}\n" +
                "Sampling flag: {}\n" +
                "Composite ID: {}\n",
                parentSpan, traceId, spanName, spanId, samplingFlag, compositeId);
    }

}
