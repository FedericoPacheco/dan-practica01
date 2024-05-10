# ./mvnw clean install
# docker build -t app .
# docker run -d -p 8080:8080 --name app_c1 app
FROM eclipse-temurin:21-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ] 