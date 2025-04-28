FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
WORKDIR /home/app

COPY ./pom.xml /home/app/pom.xml
COPY ./src/main/java/com/moldavets/url_shortener_api/UrlShortenerApiApplication.java /home/app/src/main/java/com/moldavets/url_shortener_api/UrlShortenerApiApplication.java

RUN mvn -f /home/app/pom.xml clean package -DskipTests

COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-alpine
EXPOSE 8000
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]