# Imagen base de JDK
FROM openjdk:17-jdk-slim-buster

# Argumento para la versión de la aplicación
ARG JAR_FILE=target/demodevops-0.0.1-SNAPSHOT.jar

# Copiar el archivo JAR generado en la etapa de construcción
COPY ${JAR_FILE} /app/app.jar
WORKDIR /app
EXPOSE 8080

## ejecutar app
ENTRYPOINT ["java", "-jar", "app.jar"]