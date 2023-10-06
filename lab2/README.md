# Lab 2
## Disclaimer
Exercise 2.3 a\) is at the `lab2_3a` folder instead of the `lab2_3/lab2_3_a` one, because it's a simple exercise with no changes made. That way, paragraph b\) and c\) can be combined into a single `lab2_3` project.

## Deploy a Tomcat `.war`-based Docker container
### `Dockerfile`
```dockerfile
FROM tomcat:11.0-jdk21-openjdk
EXPOSE 8080
EXPOSE 5005
ARG JAVA_OPTS
ENV JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
ENTRYPOINT ["catalina.sh","run"]
```

Run with `docker run -p 8080:8080 -v [path_to_project]/target:/usr/local/tomcat/webapps`

### Docker Compose (`compose.yaml`)
```yaml
services:
  tomcat-11-jdk21-openjdk:
    image: tomcat:11.0-jdk21-openjdk
    ports:
      # expose tomcat port 8080(container) on host as port 8888(host)
      - "8080:8080"
      # expose java debugging port 5005 on host as port 5005  (HOST:CONTAINER)
      - "5005:5005"
    command: "catalina.sh run"
    volumes:
      # "host path to the directory with .war file" / "container tomcat directory with webapps"
      - "./target:/usr/local/tomcat/webapps"
    environment:
      JAVA_OPTS: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
```

## Spring Boot
Use IntelliJ to create a new Spring project.

### Project dependencies
- Spring Boot DevTools
- Spring Web
- Thymeleaf

### Web controller
```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

}
```

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head> 
    <title>Getting Started: Serving Web Content</title> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
    <p th:text="'Hello, ' + ${name} + '!'" />
</body>
</html>
```

### Serve static pages
Place static HTML, CSS and JS files inside the `resources` folder.

### REST controller, with JSON output
```java
public record RestGreeting(long id, String content) { }
```

```java
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestEndpointController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/restgreeting")
    public RestGreeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new RestGreeting(counter.incrementAndGet(), String.format(template, name));
    }
}
```