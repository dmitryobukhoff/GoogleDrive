FROM maven:3.9.8-amazoncorretto-17 as build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim

RUN adduser --system api-gateway-user && addgroup --system api-gateway-group && adduser api-gateway-user api-gateway-group
USER api-gateway-user

WORKDIR /app

COPY --from=build target/api-gateway-0.0.1-SNAPSHOT.jar ./api-gateway.jar

ENTRYPOINT ["java", "-jar", "./api-gateway.jar"]