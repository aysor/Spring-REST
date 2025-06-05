FROM openjdk:17-jdk-alpine
LABEL authors="Aysel"
EXPOSE 8080

COPY target/SpringRest-0.0.1-SNAPSHOT.jar myapp.jar
CMD ["java", "-jar", "myapp.jar"]