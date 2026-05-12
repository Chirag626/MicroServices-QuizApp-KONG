FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY pom.xml ./pom.xml
COPY quiz-service/pom.xml ./quiz-service/pom.xml
COPY quiz-service/src ./quiz-service/src

RUN mvn -f quiz-service/pom.xml package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/quiz-service/target/quiz-service-1.0.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]