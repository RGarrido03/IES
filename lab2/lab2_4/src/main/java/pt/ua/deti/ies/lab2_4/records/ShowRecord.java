package pt.ua.deti.ies.lab2_4.records;

import java.util.List;

public record ShowRecord(long id, String name, List<QuoteRecord> quotes) {
}
