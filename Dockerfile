FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn clean install -DskipTests=true

FROM java:8

WORKDIR /opt/server

COPY --from=MAVEN_TOOL_CHAIN /tmp/target/demo-0.0.1-SNAPSHOT.jar /opt/server/demo-0.0.1-SNAPSHOT.jar


ENV DB_CONNECTION=jdbc:postgresql://165.22.89.115:5432/postgres
ENV DB_USER=postgres
ENV DB_PASSWORD=1

EXPOSE 8080
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]