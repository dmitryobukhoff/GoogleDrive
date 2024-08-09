FROM maven:3.9.8-amazoncorretto-17 as build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim

RUN adduser --system auth-service-user && addgroup --system auth-service-group && adduser auth-service-user auth-service-group
USER auth-service-user

WORKDIR /app

COPY --from=build target/auth-service-0.0.1-SNAPSHOT.jar ./auth-service.jar

ENTRYPOINT ["java", "-jar", "./auth-service.jar"]
