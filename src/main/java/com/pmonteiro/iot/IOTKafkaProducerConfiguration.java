package com.pmonteiro.iot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pmonteiro.iot.kafka.KafkaConfiguration;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class IOTKafkaProducerConfiguration extends Configuration {

    @JsonProperty("kafka")
    private KafkaConfiguration kafkaConfiguration;

    @JsonProperty("swagger")
    private SwaggerBundleConfiguration swaggerBundleConfiguration;

    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }

    public KafkaConfiguration getKafkaConfiguration() {
        return kafkaConfiguration;
    }
}
