package com.crop_service.service;

import com.crop_service.dto.CropDTO;
import com.crop_service.entity.CropStatus;
import java.util.List;

public interface CropService {
    CropDTO registerCrop(CropDTO cropDTO);
    CropDTO updateCropStatus(String id, CropStatus status);
    List<CropDTO> getAllCrops(String userId);
}