package com.automation_service.dto;

import lombok.Data;

@Data
public class TelemetryData {
    private String deviceId;
    private String zoneId;
    private Value value;

    @Data
    public static class Value {
        private double temperature;
        private double humidity;
    }
}
