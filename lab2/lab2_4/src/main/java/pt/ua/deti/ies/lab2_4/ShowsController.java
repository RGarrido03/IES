package pt.ua.deti.ies.lab2_4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ShowsController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/shows")
    public List<ShowsRecord> shows() {
        counter.set(-1);
        return List.of(new ShowsRecord(counter.incrementAndGet(), "Braveheart (1995)",
                                       "Every man dies; not every man really lives."),
                       new ShowsRecord(counter.incrementAndGet(), "Harry Potter and the Goblet of Fire (2005)",
                                       "What's comin' will come, and we'll meet it when it does."),
                       new ShowsRecord(counter.incrementAndGet(), "The Hunger Games (2012)", null));
    }
}
