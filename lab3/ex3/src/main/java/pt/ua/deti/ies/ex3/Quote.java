package pt.ua.deti.ies.ex3;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QUOTE")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Movie movie;

    @Override
    public String toString() {
        return content;
    }
}
