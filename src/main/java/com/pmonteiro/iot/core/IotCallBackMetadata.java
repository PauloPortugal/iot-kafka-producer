package com.pmonteiro.iot.core;

import org.apache.kafka.clients.producer.RecordMetadata;

public class IotCallBackMetadata {

    private long elapsedTime;
    private final String topic;
    private final int partition;
    private final long offset;
    private final long timestamp;

    public IotCallBackMetadata(long elapsedTime, RecordMetadata metadata) {
        this.elapsedTime = elapsedTime;
        this.topic = metadata.topic();
        this.partition = metadata.partition();
        this.offset = metadata.offset();
        this.timestamp = metadata.timestamp();
    }

    @Override
    public String toString() {
        return "IotCallBackMetadata{" +
                "elapsedTime=" + elapsedTime +
                ", topic='" + topic + '\'' +
                ", partition=" + partition +
                ", offset=" + offset +
                ", timestamp=" + timestamp +
                '}';
    }
}
