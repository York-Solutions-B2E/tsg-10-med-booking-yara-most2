#FROM openjdk:17-alpine
#
#WORKDIR /app

# Stage 1: Build the application with Maven
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the project files and build the application
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime environment
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the backend port
EXPOSE 8080

# Start the Spring Boot application
CMD ["java", "-jar", "app.jar"]