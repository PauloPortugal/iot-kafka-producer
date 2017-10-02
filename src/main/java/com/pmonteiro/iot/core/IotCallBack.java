package com.pmonteiro.iot.core;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.LoggerFactory;

public class IotCallBack implements Callback {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(IotCallBack.class);
    private final long startTime;

    public IotCallBack() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if (metadata == null) {
            logger.error("Callback did not return expected data", exception);
        } else {
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info(new IotCallBackMetadata(elapsedTime, metadata).toString());
        }
    }
}
