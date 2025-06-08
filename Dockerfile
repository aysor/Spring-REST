FROM openjdk:17-jdk-alpine
LABEL authors="Aysel"

EXPOSE 8081
ADD target/SpringRest-0.0.1-SNAPSHOT.jar prodapp.jar
ENTRYPOINT ["java","-jar","/prodapp.jar"]