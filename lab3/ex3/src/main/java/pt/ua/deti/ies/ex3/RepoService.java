package pt.ua.deti.ies.ex3;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepoService implements RepoServiceInterface {
    private final MovieRepository movieRepository;
    private final QuoteRepository quoteRepository;

    public RepoService(MovieRepository movieRepository, QuoteRepository quoteRepository) {
        this.movieRepository = movieRepository;
        this.quoteRepository = quoteRepository;
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Quote createQuote(Quote quote) {
        Movie movie = movieRepository.findById(quote.getMovie().getId())
                                     .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        quote.setMovie(movie);
        return quoteRepository.save(quote);
    }

    @Override
    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    @Override
    public Quote getRandomQuote() {
        int rand = (int)Math.round(Math.random() * (quoteRepository.count() - 1) + 1);
        return quoteRepository.findById(rand).orElse(null);
    }

    @Override
    public List<Quote> findQuotesByMovieId(int movieId) {
        Optional<Movie> existingOpt = movieRepository.findById(movieId);

        if (existingOpt.isPresent()) {
            return quoteRepository.findByMovie_Id(movieId);
        } else {
            return List.of();
        }
    }
}
