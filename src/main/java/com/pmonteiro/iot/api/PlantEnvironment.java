package com.pmonteiro.iot.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;

import javax.ws.rs.WebApplicationException;

public class PlantEnvironment {

    @JsonProperty("air_temperature")
    @ApiModelProperty(example = "26.78")
    private String airTemperature;

    @ApiModelProperty(example = "56.08")
    @JsonProperty("air_humidity")
    private String airHumidity;

    @JsonProperty("soil_moisture")
    @ApiModelProperty(example = "350.00")
    private String soilMoisture;

    public PlantEnvironment() {
        // needed for Jackson deserialization
    }

    public PlantEnvironment(String airTemperature, String airHumidity, String soilMoisture) {
        this.airTemperature = airTemperature;
        this.airHumidity = airHumidity;
        this.soilMoisture = soilMoisture;
    }

    public String getAirTemperature() {
        return airTemperature;
    }

    public String getAirHumidity() {
        return airHumidity;
    }

    public String getSoilMoisture() {
        return soilMoisture;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new WebApplicationException("Unprocessable entity", 422);
        }
    }
}
