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
    <id>rgarrido03</id>
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