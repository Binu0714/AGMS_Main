package com.zone_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneDTO {
    private String id;
    private String name;
    private double minTemp;
    private double maxTemp;
    private String deviceId;
    private String userId;
}