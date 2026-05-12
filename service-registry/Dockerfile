FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Parent POM copy karo
COPY pom.xml ./pom.xml
COPY service-registry/pom.xml ./service-registry/pom.xml
COPY service-registry/src ./service-registry/src

RUN mvn -f service-registry/pom.xml package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/service-registry/target/service-registry-1.0.0.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]