package pt.ua.deti.ies.ex3;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/")
public class Controller {
    private RepoServiceInterface repoServiceInterface;

    // Quote
    @PostMapping("quotes")
    public ResponseEntity<Quote> createQuote(@RequestBody Quote quote) {
        try {
            Quote newQuote = repoServiceInterface.createQuote(quote);
            return new ResponseEntity<>(newQuote, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("quote")
    public ResponseEntity<Quote> getRandomQuote() {
        Quote quote = repoServiceInterface.getRandomQuote();
        return new ResponseEntity<>(quote, quote != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("quotes")
    public ResponseEntity<List<Quote>> getQuotesByMovie(@RequestParam(name = "show", required = false) String movieId) {
        List<Quote> quoteList;

        if (movieId != null) {
            int id = Integer.parseInt(movieId);
            quoteList = repoServiceInterface.findQuotesByMovieId(id);
            return new ResponseEntity<>(quoteList, !quoteList.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        }

        quoteList = repoServiceInterface.getAllQuotes();
        return new ResponseEntity<>(quoteList, quoteList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    // Movie
    @PostMapping("shows")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie newMovie = repoServiceInterface.createMovie(movie);
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    @GetMapping("shows")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movieList = repoServiceInterface.getAllMovies();
        return new ResponseEntity<>(movieList, movieList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
