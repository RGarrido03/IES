# Lab 1
## Create Maven projects in IntelliJ
In the welcome page:
- Click on *New Project*
- Choose Maven on the sidebar
- Give the project a name
- Choose the archetype that ends in `-quickstart`
- Change `groupId` to `pt.ua.deti.ies`
- Click on *Create*

## Create Maven projects in the CLI
```console
mvn archetype:generate -DgroupId=pt.ua.deti.ies -DartifactId=APP_NAME -
DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

## Needed-to-be-added properties in `pom.xml`
```xml
<properties>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <maven.compiler.source>21</maven.compiler.source>
  <maven.compiler.target>21</maven.compiler.target>
</properties>

<developers>
  <developer>
    <id>RGarrido03</id>
    <name>Rúben Garrido</name>
    <email>rubengarrido@ua.pt</email>
    <organization>Universidade de Aveiro</organization>
    <organizationUrl>https://www.ua.pt</organizationUrl>
    <roles>
        <role>developer</role>
    </roles>
    <timezone>0</timezone>
  </developer>
</developers>
```

## Log4j
### Recommended `log4j2.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- Extra logging related to initialization of Log4j.
     Set to debug or trace if log4j initialization is failing. -->
<Configuration status="warn">
    <Appenders>
        <!-- Console appender configuration -->
        <Console name="console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n" />
        </Console>
    </Appenders>
    <Loggers>
        <!-- Root logger referring to console appender -->
        <Root level="debug" additivity="false">
            <AppenderRef ref="console" />
        </Root>
    </Loggers>
</Configuration>
```

### Dependencies
```xml
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-api</artifactId>
  <version>2.20.0</version>
</dependency>
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-core</artifactId>
  <version>2.20.0</version>
</dependency>
```

### Main class variables and imports
```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Replace "Main" with the main class name
private static final Logger logger = LogManager.getLogger(Main.class);
```

## Dockerizing a Java app
### `Dockerfile`
```dockerfile
FROM openjdk:21
COPY target/[jar_name].jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### `pom.xml` properties
```xml
<build>
  <finalName>[jar_name]</finalName>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-jar-plugin</artifactId>
      <version>3.3.0</version>
      <configuration>
        <archive>
          <manifest>
            <mainClass>[main_class_path]</mainClass>
          </manifest>
        </archive>
      </configuration>
    </plugin>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-shade-plugin</artifactId>
      <version>3.5.0</version>
      <executions>
        <execution>
          <phase>package</phase>
          <goals>
            <goal>shade</goal>
          </goals>
          <configuration>
            <transformers>
              <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                <mainClass>[main_class_path]</mainClass>
              </transformer>
            </transformers>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```