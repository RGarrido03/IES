FROM ubuntu:latest
LABEL authors="ruben"

FROM openjdk:21
COPY target/anycityforecast.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]