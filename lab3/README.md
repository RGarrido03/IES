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