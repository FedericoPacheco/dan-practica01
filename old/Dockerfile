# ./mvnw clean install
# docker build -t practica1-service .
# docker run -d -p 8080:8080 --name practica1-service practica1-service
FROM eclipse-temurin:21-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} practica1-service.jar
ENTRYPOINT [ "java", "-jar", "/practica1-service.jar" ] 