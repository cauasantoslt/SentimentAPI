FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY target/*.jar app.jar

ENV SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]