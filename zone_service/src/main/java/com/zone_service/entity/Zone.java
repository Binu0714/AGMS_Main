package com.zone_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Zone {
    @Id
    private String id;
    private String name;
    private double minTemp;
    private double maxTemp;
    private String deviceId;
    private String userId;
}