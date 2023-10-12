package pt.ua.deti.ies.lab2_4;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.ies.lab2_4.records.ErrorRecord;

@RestController
public class QuotesController {
    @GetMapping("/api/quotes")
    public Object shows(@RequestParam(value = "show", defaultValue = "0") String show, HttpServletResponse response) {
        if (show.matches("[0-9]*")) {
            int id = Integer.parseInt(show);

            if (id >= 0 && id < Data.getSize()) {
                return Data.getShowQuotes(id);
            }

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorRecord(HttpServletResponse.SC_BAD_REQUEST, "Show ID is out of range.");
        }

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new ErrorRecord(HttpServletResponse.SC_BAD_REQUEST, "Show ID is invalid.");
    }
}
