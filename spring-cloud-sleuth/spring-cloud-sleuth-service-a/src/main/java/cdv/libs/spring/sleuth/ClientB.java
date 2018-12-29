package cdv.libs.spring.sleuth;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * HTTP client for service B
 *
 * @author Dmitry Kulga
 *         29.12.2018 21:23
 */
@FeignClient(name = "client-b", url = "${serviceB.url}")
public interface ClientB {

    @RequestMapping(method = RequestMethod.GET, value = "/service/b")
    void callServiceB();

}
