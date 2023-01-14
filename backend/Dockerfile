#
# Build stage
#
RUN mvn clean install
FROM maven:3.8.6-jdk-17 AS build
COPY . .
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17
ADD target/*.jar backend
ENTRYPOINT ["java", "-jar","backend"]
EXPOSE 8080