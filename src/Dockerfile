FROM java:8
COPY demo-0.0.1-SNAPSHOT.jar /apps/order-back/
WORKDIR /apps/spring_app


ENV DB_CONNECTION=jdbc:postgresql://10.129.0.10:5432/postgres
ENV DB_USER=postgres
ENV DB_PASSWORD=1


EXPOSE 8080
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]