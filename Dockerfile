# Building - With Maven
FROM maven

# Build
RUN mkdir /backend-src
COPY . /backend-src
WORKDIR /backend-src
RUN mvn --no-transfer-progress clean package -DskipTests=true

# Getting base image
FROM openjdk:17 AS build

# Server port
EXPOSE 8090

# Copy jar file from local to image
RUN mkdir /backend
COPY target/backend-start-api.jar /backend/backend-start-api.jar

ENTRYPOINT ["java", "-jar", "/backend/backend-start-api.jar"]
