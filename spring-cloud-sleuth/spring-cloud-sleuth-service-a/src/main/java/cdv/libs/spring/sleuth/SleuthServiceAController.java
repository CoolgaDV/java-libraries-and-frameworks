package cdv.libs.spring.sleuth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that accepts HTTP request and calls service B
 *
 * @author Dmitry Kulga
 *         29.12.2018 21:14
 */
@RestController
@RequiredArgsConstructor
public class SleuthServiceAController {

    private final ClientB client;

    @GetMapping("service/a")
    public void call() {
        client.callServiceB();
    }

}
