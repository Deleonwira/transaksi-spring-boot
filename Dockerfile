# ========== STAGE 1: BUILD DENGAN MAVEN ==========
FROM maven:3.9-eclipse-temurin-25-alpine AS build

WORKDIR /app

# Copy pom dan source
COPY pom.xml .
COPY src ./src

# Build jar (skip test kalau mau cepat)
RUN mvn -B -DskipTests package

# ========== STAGE 2: RUNTIME JAR ==========
FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

# Copy jar hasil build dari stage 1
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
