# Generar .jar:
# mvn clean package -DskipTests
# Es importante evitar los tests porque fallan por como esta 
# configurado application.properties (para uso con contenedor)
FROM eclipse-temurin:21-jdk-alpine
COPY target/*.jar practica1_app.jar
ENTRYPOINT [ "java", "-jar", "/practica1_app.jar" ] 