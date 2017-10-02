FROM openjdk:8-jre-alpine
MAINTAINER https://hub.docker.com/u/pauloportugal/

RUN apk update
RUN apk upgrade

RUN apk add bash
RUN apk add curl

ENV JAVA_HOME /usr/lib/jvm/java-8-*/
ENV PORT 8080
ENV ADMIN_PORT 8091

EXPOSE 8080
EXPOSE 8091

WORKDIR /app

COPY target/config.yml /app/
ADD target/iot-kafka-producer-*-uber.jar /app/

CMD java -jar iot-kafka-producer-*-uber.jar server config.yml