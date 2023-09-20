FROM openjdk:17-jdk-slim
COPY ./target/transfer-service-0.0.1-SNAPSHOT.jar transfer-service.jar
EXPOSE 8080
CMD ["java", "-jar", "transfer-service.jar"]
