FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app


# ✅ Parent POM
COPY pom.xml ./pom.xml

# ✅ ALL child POMs
COPY question-service/pom.xml ./question-service/pom.xml
COPY quiz-service/pom.xml ./quiz-service/pom.xml
COPY hello-service/pom.xml ./hello-service/pom.xml

# ✅ Resolve dependencies
RUN mvn dependency:go-offline -pl quiz-service -am -q

# ✅ Copy source
COPY quiz-service/src ./quiz-service/src


# ✅ Build the JAR
# Step 1 - Start from Java 21 base image
RUN mvn clean package -pl quiz-service -am -DskipTests

# Step 2 - Set working directory inside container
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Step 3 - Copy the JAR into container
COPY --from=build /app/quiz-service/target/quiz-service-1.0.0.jar app.jar

# Step 4 - Expose the port
EXPOSE 8081

# Step 5 - Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]