FROM amazoncorretto:11 as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} serviceparts.jar
ENTRYPOINT ["java","-jar","/serviceparts.jar"]