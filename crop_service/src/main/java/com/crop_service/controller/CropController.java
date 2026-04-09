package com.crop_service.controller;

import com.crop_service.dto.ApiResponse;
import com.crop_service.dto.CropDTO;
import com.crop_service.entity.CropStatus;
import com.crop_service.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    @Autowired
    private CropService cropService;

    @PostMapping
    public ResponseEntity<ApiResponse> register(@RequestBody CropDTO cropDTO) {
        return ResponseEntity.ok(new ApiResponse(201, "Crop Registered", cropService.registerCrop(cropDTO)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable String id, @RequestParam CropStatus status) {
        return ResponseEntity.ok(new ApiResponse(200, "Status Updated", cropService.updateCropStatus(id, status)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getInventory(@PathVariable String userId) {
        return ResponseEntity.ok(new ApiResponse(200, "Inventory Fetched", cropService.getAllCrops(userId)));
    }
}