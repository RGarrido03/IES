# Lab 3
## Exercise 1 c\)
**The “UserController” class gets an instance of “userRepository” through its constructor; how is this new repository instantiated?**\
By using the `@Autowired` annotation, one can pass the repository as argument and Spring Boot will automatically populate the controller with the respective repository.
```java
private final UserRepository userRepository;

@Autowired
public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
}
```

**List the methods invoked in the “userRepository” object by the “UserController”. Where are these methods defined?**
```java
save(User user)
findAll()
findById(long id)
```
These methods are defined in the `UserRepository` interface, which extends `CrudRepository`.


**Where is the data being saved?**\
The data is being saved in an H2 database, and is used by the client as a repository (in this case, the `UserRepository` interface):
```java
@Repository
public interface UserRepository extends CrudRepository<User, Long> {}
```
The table name is defined in the `@Entity` annotation as a parameter:
```java
@Entity(name = "tbl_user")
public class User {
    ...
}
```

**Where is the rule for the “not empty” email address defined?**\
The "not empty" rule is defined as a `@NotBlank` annotation in the `User` class, before declaring the `email` variable.
```java
@NotBlank(message = "Email is mandatory")
private String email;
```

## JPA database structure
### Entity classes
They include `@Entity`, `@Table`, `@Column`, `@JoinColumn` and relationships such as `@ManyToOne`.
```java
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
```

**Pro-tip**: `lombok` annotations are quite powerful and reduce the need to define default getters, setters and constructors.

### Repository
Extends `JpaRepository` and implements new non-default functions.
```java
public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    List<Quote> findByMovie_Id(int movieId);
}
```

### Service
Self-explanatory.
```java
@Service
public class RepoService implements RepoServiceInterface {
    private final ExtendedRepositoryInterface repo; // One per entity
    
    // Various functions regarding the repo, such as:
    @Override
    public Movie createMovie(Movie movie) {
        return repo.save(movie);
    }
}
```

### REST Controller
API mapping itself.
```java
@RestController
@AllArgsConstructor
@RequestMapping("api/")
public class Controller {
    private RepoServiceInterface repoServiceInterface;

    // Use POST, GET, etc. annotations, to define each endpoint.

    @PostMapping("shows")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(repoServiceInterface.createMovie(movie), HttpStatus.CREATED);
    }

    @GetMapping("shows")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movieList = repoServiceInterface.getAllMovies();
        return new ResponseEntity<>(movieList, movieList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    // And add request parameters

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
}
```

## Dockerize Spring Boot app with MySQL 8 and JDK 21
`Dockerfile` to build the app:
```dockerfile
FROM maven:3.9.5-amazoncorretto-21

# Don't run mvn clean install or else it will fail due to communications link failure.
COPY . .
CMD mvn spring-boot:run
```

`compose.yaml`:
```yaml
services:
  mysqldb:
    image: mysql/mysql-server:latest-aarch64
    ports:
      - 3306:3306 # Keep it to use inside IntelliJ
    env_file: .env
    environment:
      MYSQL_ROOT_PASSWORD: $MYSQL_ROOT_PASSWORD
      MYSQL_DATABASE: $MYSQL_DATABASE
      MYSQL_USER: $MYSQL_USER
      MYSQL_PASSWORD: $MYSQL_PASSWORD
    healthcheck:
      test: [ "CMD-SHELL", 'mysqladmin ping' ]
      interval: 10s
      timeout: 2s
      retries: 10
    networks:
      - backend
    volumes:
      - mysqlvolume:/var/lib/mysql
  api:
    build:
      context: ./
      dockerfile: ./Dockerfile
    depends_on:
      mysqldb:
        condition: service_healthy
    restart: on-failure
    ports:
      - 8080:8080
    env_file: .env
    environment:
      SPRING_APPLICATION_JSON: '{
            "spring.datasource.url": "jdbc:mysql://mysqldb:3306/$MYSQL_DATABASE",
            "spring.datasource.username": "$MYSQL_USER",
            "spring.datasource.password": "$MYSQL_PASSWORD",
            "spring.jpa.hibernate.ddl-auto": "update"
          }'
    networks:
      - backend

networks:
  backend:

volumes:
  mysqlvolume:
```