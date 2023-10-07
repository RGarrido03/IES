package pt.ua.deti.ies.lab2_4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuotesController {

    private final List<List<QuoteRecord>> shows = List.of(
            List.of(
                    new QuoteRecord(0, "Every man dies; not every man really lives."),
                    new QuoteRecord(1, "I warned your grandfather of what his greed would summon, but he would not listen.")
            ),
            List.of(
                    new QuoteRecord(0, "What's comin' will come, and we'll meet it when it does."),
                    new QuoteRecord(1, "When life gets you down, you know what you gotta do? Just keep swimming."),
                    new QuoteRecord(2, "Life’s a little bit messy. We all make mistakes. No matter what type of animal you are, change starts with you.")
            ),
            List.of(
                    new QuoteRecord(0, "I'll have what he's having.")
            )
    );

    @GetMapping("/quotes")
    public List<QuoteRecord> shows(@RequestParam(value = "show", defaultValue = "0") String show) {
        if (show.matches("[0-9]*")) {
            int id = Integer.parseInt(show);

            if (id >= 0 && id < shows.size()) {
                return shows.get(id);
            }
        }

        return List.of();
    }
}
