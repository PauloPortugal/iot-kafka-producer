package com.pmonteiro.iot.resources;

import com.google.common.collect.ImmutableMap;
import com.pmonteiro.iot.api.PlantEnvironment;
import com.pmonteiro.iot.core.IotCallBack;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class IotKafkaProducerResourceTest {
    private static final KafkaProducer kafkaProducer = mock(KafkaProducer.class);
    private static final String TOPIC = "iot";
    public static final String AIR_TEMPERATURE_READING = "28.00";
    public static final String AIR_HUMIDITY_READING = "48.9";
    public static final String SOIL_MOISTURE_READING = "300.67";
    public static final String INVALID_READING = "invalid";
    public static final String AIR_TEMPERATURE_KEY = "air_temperature";
    public static final String AIR_HUMIDITY_KEY = "air_humidity";
    public static final String SOIL_MOISTURE_KEY = "soil_moisture";
    private ProducerRecord producerRecord;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new IotKafkaProducerResource(kafkaProducer, TOPIC))
            .build();

    @Test
    public void should_write() throws Exception {
        producerRecord = new ProducerRecord(TOPIC, new PlantEnvironment(AIR_TEMPERATURE_READING, AIR_HUMIDITY_READING, SOIL_MOISTURE_READING).toJson());
        ImmutableMap<String, String> payload = ImmutableMap.of(
                AIR_TEMPERATURE_KEY, AIR_TEMPERATURE_READING,
                AIR_HUMIDITY_KEY, AIR_HUMIDITY_READING,
                SOIL_MOISTURE_KEY, SOIL_MOISTURE_READING);
        assertThat(resources.target("/iot").request().post(Entity.json(payload)))
                .hasFieldOrPropertyWithValue("status", 202);
        verify(kafkaProducer).send(eq(producerRecord), any(IotCallBack.class));
    }

    @Test
    public void should_write_ifSoilMoistureReadingsAreInvalid() throws Exception {
        producerRecord = new ProducerRecord(TOPIC, new PlantEnvironment(AIR_TEMPERATURE_READING, AIR_HUMIDITY_READING, INVALID_READING).toJson());
        ImmutableMap<String, String> payload = ImmutableMap.of(
                AIR_TEMPERATURE_KEY, AIR_TEMPERATURE_READING,
                AIR_HUMIDITY_KEY, AIR_HUMIDITY_READING,
                SOIL_MOISTURE_KEY, INVALID_READING);
        assertThat(resources.target("/iot").request().post(Entity.json(payload)))
                .hasFieldOrPropertyWithValue("status", 202);
        verify(kafkaProducer).send(eq(producerRecord), any(IotCallBack.class));
    }

    @Test
    public void should_write_ifAirReadingsAreInvalid() throws Exception {
        producerRecord = new ProducerRecord(TOPIC, new PlantEnvironment(INVALID_READING, INVALID_READING, SOIL_MOISTURE_READING).toJson());
        ImmutableMap<String, String> payload = ImmutableMap.of(
                AIR_TEMPERATURE_KEY, INVALID_READING,
                AIR_HUMIDITY_KEY, INVALID_READING,
                SOIL_MOISTURE_KEY, SOIL_MOISTURE_READING);
        assertThat(resources.target("/iot").request().post(Entity.json(payload)))
                .hasFieldOrPropertyWithValue("status", 202);
        verify(kafkaProducer).send(eq(producerRecord), any(IotCallBack.class));
    }

    @Test
    public void should_write_ifAllReadingsAreInvalid() throws Exception {
        producerRecord = new ProducerRecord(TOPIC, new PlantEnvironment(INVALID_READING, INVALID_READING, SOIL_MOISTURE_READING).toJson());
        ImmutableMap<String, String> payload = ImmutableMap.of(
                AIR_TEMPERATURE_KEY, INVALID_READING,
                AIR_HUMIDITY_KEY, INVALID_READING,
                SOIL_MOISTURE_KEY, INVALID_READING);
        assertThat(resources.target("/iot").request().post(Entity.json(payload)))
                .hasFieldOrPropertyWithValue("status", 202);
        verify(kafkaProducer).send(eq(producerRecord), any(IotCallBack.class));
    }
}
