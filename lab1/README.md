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
  <maven.compiler.source>20</maven.compiler.source>
  <maven.compiler.target>20</maven.compiler.target>
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

## Recommended `log4j2.xml` configuration:
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