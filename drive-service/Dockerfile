FROM maven:3.9.8-amazoncorretto-17 as build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim

RUN adduser --system drive-service-user && addgroup --system drive-service-group && adduser drive-service-user drive-service-group
USER drive-service-user

WORKDIR /app

COPY --from=build target/drive-service-0.0.1-SNAPSHOT.jar ./drive-service.jar

ENTRYPOINT ["java", "-jar", "./drive-service.jar"]
