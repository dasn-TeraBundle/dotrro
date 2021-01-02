FROM openjdk:11.0-slim-buster
MAINTAINER Nirupam

WORKDIR /opt
ADD target/gateway-1.0.0-SNAPSHOT.jar /opt

EXPOSE 8081

CMD ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "-Dspring.cloud.config.uri=${CONFIG_URI}","/opt/gateway-1.0.0-SNAPSHOT.jar"]