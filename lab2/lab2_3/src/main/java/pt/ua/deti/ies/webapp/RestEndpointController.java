package pt.ua.deti.ies.webapp;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestEndpointController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/restgreeting")
    public RestGreeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new RestGreeting(counter.incrementAndGet(), String.format(template, name));
    }
}
