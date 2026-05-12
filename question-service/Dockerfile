FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml ./pom.xml
COPY question-service/pom.xml ./question-service/pom.xml
COPY question-service/src ./question-service/src

# Step 1 - Start from Java 21 base image
RUN mvn -f question-service/pom.xml package -DskipTests

# Step 2 - Set working directory inside container
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Step 3 - Copy the JAR into container
COPY --from=build /app/question-service/target/question-service-1.0.0.jar app.jar

# Step 4 - Expose the port
EXPOSE 8080

# Step 5 - Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]