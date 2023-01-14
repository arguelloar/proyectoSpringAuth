#
# Build stage
#
FROM maven:3.8.6-amazoncorretto-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17
COPY --from=build /target/project-0.0.1-SNAPSHOT.jar project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","project.jar"]