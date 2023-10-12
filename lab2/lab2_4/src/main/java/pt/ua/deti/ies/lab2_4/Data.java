package pt.ua.deti.ies.lab2_4;

import pt.ua.deti.ies.lab2_4.records.QuoteRecord;
import pt.ua.deti.ies.lab2_4.records.ShowRecord;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Data {
    private static final AtomicLong showCounter = new AtomicLong();
    private static final AtomicLong quoteCounter = new AtomicLong();

    private static final List<ShowRecord> data = List.of(
            new ShowRecord(
                    showCounter.incrementAndGet(),
                    "Braveheart (1995)",
                    List.of(
                            new QuoteRecord(quoteCounter.incrementAndGet(), "Every man dies; not every man really lives."),
                            new QuoteRecord(quoteCounter.incrementAndGet(), "I warned your grandfather of what his greed would summon, but he would not listen.")
                    )
            ),
            new ShowRecord(
                    showCounter.incrementAndGet(),
                    "Harry Potter and the Goblet of Fire (2005)",
                    List.of(
                            new QuoteRecord(quoteCounter.incrementAndGet(), "What's comin' will come, and we'll meet it when it does."),
                            new QuoteRecord(quoteCounter.incrementAndGet(), "When life gets you down, you know what you gotta do? Just keep swimming."),
                            new QuoteRecord(quoteCounter.incrementAndGet(), "When life gets you down, you know what you gotta do? Just keep swimming.")
                    )
            ),
            new ShowRecord(
                    showCounter.incrementAndGet(),
                    "The Hunger Games (2012)",
                    List.of(new QuoteRecord(quoteCounter.incrementAndGet(), "I volunteer! I volunteer as tribute.")))
    );

    public static QuoteRecord getRandomQuote() {
        List<QuoteRecord> quotesList = data.stream().map(ShowRecord::quotes).flatMap(Collection::stream).toList();

        int randId = new Random().nextInt(quotesList.size());
        return quotesList.get(randId);
    }

    public static List<String> getShowsList() {
        return data.stream().map(ShowRecord::name).toList();
    }

    public static List<QuoteRecord> getShowQuotes(int id) {
        return Data.data.get(id).quotes();
    }

    public static int getSize() {
        return Data.data.size();
    }
}