package com.sensor_service.dto;
import lombok.Data;

@Data
public class TelemetryData {
    private String deviceId;
    private String zoneId;
    private TelemetryValue value;
    private String capturedAt;
}