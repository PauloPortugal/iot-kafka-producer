package com.pmonteiro.iot.resources;

import com.codahale.metrics.annotation.Timed;
import com.pmonteiro.iot.api.PlantEnvironment;
import com.pmonteiro.iot.core.IotCallBack;
import io.swagger.annotations.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Api("/")
@Path("iot")
@Produces(APPLICATION_JSON)
public class IotKafkaProducerResource {
    private String topic;
    private KafkaProducer kafkaProducer;

    public IotKafkaProducerResource(KafkaProducer kafkaProducer, String topic) {
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;
    }

    @POST
    @Timed
    @Consumes(APPLICATION_JSON)
    @ApiOperation(
            value = "Writes IOT messages to the Kafka Producer")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 422, message = "Unprocessable entity")})
    public Response write(@ApiParam(value = "payload", required = true) PlantEnvironment plantEnvironment) {
        kafkaProducer.send(new ProducerRecord(topic, plantEnvironment.toJson()), new IotCallBack());
        return Response.accepted().entity(plantEnvironment).build();
    }
}