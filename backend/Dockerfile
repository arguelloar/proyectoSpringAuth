#
# Build stage
#
FROM maven:3.8.6-jdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:11
ADD target/*.jar project
ENTRYPOINT ["java", "-jar","project"]
EXPOSE 8080