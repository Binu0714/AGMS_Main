package com.sensor_service.dto;
import lombok.Data;

@Data
public class TelemetryValue {
    private double temperature;
    private double humidity;
}