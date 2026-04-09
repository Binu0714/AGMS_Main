package com.automation_service.dto;

import lombok.Data;

@Data
public class ZoneDTO {
    private String id;
    private String name;
    private double minTemp;
    private double maxTemp;
}
