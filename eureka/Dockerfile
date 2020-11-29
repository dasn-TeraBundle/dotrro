FROM openjdk:11.0-slim-buster
MAINTAINER Nirupam

WORKDIR /opt
ADD target/eureka-1.0.0-SNAPSHOT.jar /opt

EXPOSE 8761

CMD ["java", "-jar", "/opt/eureka-1.0.0-SNAPSHOT.jar"]