package pt.ua.deti.ies.ex3;

import java.util.List;

public interface RepoServiceInterface {
    // Movie
    Movie createMovie(Movie movie);
    List<Movie> getAllMovies();

    // Quote
    Quote createQuote(Quote quote);
    List<Quote> getAllQuotes();
    Quote getRandomQuote();
    List<Quote> findQuotesByMovieId(int movieId);
}
