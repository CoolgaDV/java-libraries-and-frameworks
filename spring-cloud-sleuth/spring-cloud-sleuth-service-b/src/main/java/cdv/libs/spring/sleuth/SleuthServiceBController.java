package cdv.libs.spring.sleuth;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.MultiValueMap;
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
    public void call(@RequestHeader MultiValueMap<String, String> headers) {

        log.info("Service B is called:" +
                "\nParent span ID: " + headers.get("x-b3-parentspanid") +
                "\nTrace ID: " +       headers.get("x-b3-traceid") +
                "\nSpan name: " +      headers.get("x-span-name") +
                "\nSpan ID: " +        headers.get("x-b3-spanid") +
                "\nSampling flag: " +  headers.get("x-b3-sampled") +
                "\nComposite ID: " +   headers.get("b3"));

        log.info("Mapped Diagnostic Context:" +
                "\nParent span ID: " + MDC.get("X-B3-ParentSpanId") +
                "\nTrace ID: " +       MDC.get("X-B3-TraceId") +
                "\nExport flag: " +    MDC.get("X-Span-Export") +
                "\nSpan ID: " +        MDC.get("X-B3-SpanId"));
    }

}
