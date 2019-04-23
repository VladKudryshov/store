FROM java:8
WORKDIR /opt/sem-platform
ADD target/demo-0.0.1-SNAPSHOT.jar /opt/sem-platform/demo-0.0.1-SNAPSHOT.jar


ENV DB_CONNECTION=jdbc:postgresql://10.129.0.10:5432/postgres
ENV DB_USER=postgres
ENV DB_PASSWORD=1


EXPOSE 8081
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]