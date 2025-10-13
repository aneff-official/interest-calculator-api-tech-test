# syntax=docker/dockerfile:1

# Build stage
FROM gradle:9.1.0-jdk21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build --no-daemon

# Run stage
FROM eclipse-temurin:21.0.8_9-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
