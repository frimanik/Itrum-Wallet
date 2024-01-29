FROM openjdk:17-jdk-alpine
FROM maven:3.8.4-openjdk-17-slim AS build
EXPOSE 8080
ARG WAR_FILE=target/*.jar
COPY . .
RUN mvn clean package -DskipTests
ENTRYPOINT ["java","-jar","./target/Itrum-Wallet-0.0.1-SNAPSHOT.jar"]