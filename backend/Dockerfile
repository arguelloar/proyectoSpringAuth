#
# BUILD
#
RUN mvn clean install


#
# PACKAGE
#
FROM openjdk:17
ADD target/*.jar backend
ENTRYPOINT ["java", "-jar","backend"]
EXPOSE 8080