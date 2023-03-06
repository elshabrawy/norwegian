FROM openjdk:17.0.2-slim

COPY build/libs/*.prod.jar app.jar
ENTRYPOINT ["java","-Djava.norwegian.egd=file:/dev/./urandom","-jar","/app.jar"]