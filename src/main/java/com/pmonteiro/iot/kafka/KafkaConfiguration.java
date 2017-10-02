package com.pmonteiro.iot.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaConfiguration {

    private String server;
    @JsonProperty
    private String topic;
    @JsonProperty
    private String acks;
    @JsonProperty
    private String retries;
    @JsonProperty
    private String linger;

    public KafkaConfiguration() {
        // needed by Jackson for the serialization of the KafkaConfiguration object
    }

    public String getServer() {
        return server;
    }

    public String getTopic() {
        return topic;
    }

    public String getAcks() {
        return acks;
    }

    public String getRetries() {
        return retries;
    }

    public String getLinger() {
        return linger;
    }
}
