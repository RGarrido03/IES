package pt.ua.deti.ies.lab2_4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.ies.lab2_4.records.QuoteRecord;

@RestController
public class QuoteController {

    @GetMapping("/api/quote")
    public QuoteRecord quote() {
        return Data.getRandomQuote();
    }
}
