package com.pmonteiro.iot;

import com.pmonteiro.iot.kafka.KafkaConfiguration;
import com.pmonteiro.iot.resources.IotKafkaProducerResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class IOTKafkaProducerApplication extends Application<IOTKafkaProducerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new IOTKafkaProducerApplication().run(args);
    }

    @Override
    public String getName() {
        return "IOTKafkaProducer";
    }

    @Override
    public void initialize(final Bootstrap<IOTKafkaProducerConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<IOTKafkaProducerConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(IOTKafkaProducerConfiguration configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
    }

    @Override
    public void run(final IOTKafkaProducerConfiguration configuration,
                    final Environment environment) {
        KafkaProducer kafkaProducer = new KafkaProducer(getKafkaProperties(configuration.getKafkaConfiguration()));
        environment.jersey().register(new IotKafkaProducerResource(kafkaProducer, configuration.getKafkaConfiguration().getTopic()));
    }

    private Properties getKafkaProperties(KafkaConfiguration kafkaConfiguration) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaConfiguration.getServer());
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty("acks", kafkaConfiguration.getAcks());
        properties.setProperty("retries", kafkaConfiguration.getRetries());
        properties.setProperty("linger.ms", kafkaConfiguration.getLinger());
        return properties;
    }
}
