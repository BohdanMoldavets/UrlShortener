FROM maven:3.8.8-eclipse-temurin-21-alpine

COPY . backend/
WORKDIR backend/

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

ENTRYPOINT ["java", "-jar", "target/url-shortener-api-0.0.1-SNAPSHOT.jar"]
