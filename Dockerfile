FROM openjdk:8-jdk-alpine
MAINTAINER quiz
COPY target/quiz-0.0.1-SNAPSHOT.jar quiz-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/quiz-0.0.1-SNAPSHOT.jar"]