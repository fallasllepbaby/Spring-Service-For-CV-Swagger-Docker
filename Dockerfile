FROM eclipse-temurin:17-jdk
ADD target/Spring-Service-For-CV-Swagger-Docker-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]