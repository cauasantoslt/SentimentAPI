# Etapa 1: Build (Compilação)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Compila e cria o .jar
RUN mvn clean package -DskipTests

# Etapa 2: Runtime (Execução)
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Define variáveis de ambiente
ENV SERVER_PORT=8080

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]