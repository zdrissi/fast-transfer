FROM openjdk:17-jdk-slim

ARG JAR_FILE=fast-transfer-service/target/*.jar

COPY ${JAR_FILE} fast-transfer-service.jar

ENTRYPOINT ["java", "-jar", "/fast-transfer-service.jar"]

EXPOSE 8080