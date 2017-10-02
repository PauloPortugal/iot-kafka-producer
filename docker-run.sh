#!/bin/bash
mvn -DskipTests clean package && \
docker build -t pauloportugal/iot-kafka-producer:local . && \
docker run -ti --rm --net=host -p 8080:8080 --name iot-kafka-producer pauloportugal/iot-kafka-producer:local