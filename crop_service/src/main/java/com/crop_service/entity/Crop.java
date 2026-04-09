package com.crop_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crop {
    @Id
    private String id;
    private String cropName;
    private int quantity;
    private String userId;

    @Enumerated(EnumType.STRING)
    private CropStatus status;

    private LocalDateTime plantedDate;
}