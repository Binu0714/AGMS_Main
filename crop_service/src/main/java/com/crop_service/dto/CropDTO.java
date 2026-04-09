package com.crop_service.dto;
import com.crop_service.entity.CropStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO {
    private String id;
    private String cropName;
    private int quantity;
    private String userId;
    private CropStatus status;
    private LocalDateTime plantedDate;
}