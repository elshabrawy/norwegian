FROM openjdk:8-jdk-alpine

COPY build/libs/*.prod.jar app.jar
ENTRYPOINT ["java","-Djava.norwegian.egd=file:/dev/./urandom","-jar","/app.jar"]