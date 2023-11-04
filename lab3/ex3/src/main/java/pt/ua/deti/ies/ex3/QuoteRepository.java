package pt.ua.deti.ies.ex3;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    List<Quote> findByMovie_Id(int movieId);
}
