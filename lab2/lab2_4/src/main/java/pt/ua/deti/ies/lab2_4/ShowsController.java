package pt.ua.deti.ies.lab2_4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ShowsController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/api/shows")
    public List<String> shows() {
        counter.set(-1);
        return Data.getShowsList();
    }
}
