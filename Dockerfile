FROM java:8

WORKDIR /opt/server

ADD target/demo-0.0.1-SNAPSHOT.jar /opt/server/demo-0.0.1-SNAPSHOT.jar

ENV DB_CONNECTION=jdbc:postgresql://databasehost/postgres
ENV DB_USER=postgres
ENV DB_PASSWORD=1

EXPOSE 8080
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]