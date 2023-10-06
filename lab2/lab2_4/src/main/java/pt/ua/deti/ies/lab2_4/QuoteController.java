package pt.ua.deti.ies.lab2_4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class QuoteController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/quote")
    public QuoteRecord quote() {
        return new QuoteRecord(counter.incrementAndGet(),
                               "Being genius is not enough, it takes courage to change people's hearts.");
    }
}
