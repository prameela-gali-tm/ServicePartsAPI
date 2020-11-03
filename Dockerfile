FROM amazoncorretto:11 as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} serviceparts.jar
COPY target/classes/certs certs
ENTRYPOINT ["java","-jar","/serviceparts.jar"]