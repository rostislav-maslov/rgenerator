FROM eclipse-temurin:11-alpine

WORKDIR /app

ARG JAR_FILE=./rgenerator-api-spring-boot.jar

COPY ${JAR_FILE} rgenerator-api.jar
COPY ./application.properties application.properties

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/rgenerator-api.jar"]