FROM tomcat:11.0-jdk21-openjdk
EXPOSE 8080
EXPOSE 5005
ARG JAVA_OPTS
ENV JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
ENTRYPOINT ["catalina.sh","run"]