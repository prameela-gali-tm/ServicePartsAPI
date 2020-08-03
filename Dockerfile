FROM amazoncorretto:11
VOLUME /tmp

RUN mkdir /deployments
COPY ["target/serviceparts.jar", "/deployments"]

ENTRYPOINT java -jar /deployments/serviceparts.jar