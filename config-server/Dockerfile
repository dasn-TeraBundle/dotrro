FROM openjdk:11.0-slim-buster
MAINTAINER Nirupam

WORKDIR /opt
ADD target/config-server-1.0.0-SNAPSHOT.jar /opt
EXPOSE 8888

CMD ["java", "-jar", "/opt/config-server-1.0.0-SNAPSHOT.jar"]