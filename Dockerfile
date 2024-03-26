FROM openjdk:21-jdk-slim

ARG PATH_TO_JAR
ADD ${PATH_TO_JAR}/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]