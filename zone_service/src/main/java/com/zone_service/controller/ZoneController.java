package com.zone_service.controller;

import com.zone_service.dto.ApiResponse;
import com.zone_service.dto.ZoneDTO;
import com.zone_service.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    @Autowired private ZoneService zoneService;

    @PostMapping
    public ResponseEntity<ApiResponse> createZone(@RequestBody ZoneDTO zoneDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(new ApiResponse(201, "Zone created", zoneService.saveZone(zoneDTO, token)));
    }

    @GetMapping
    public List<ZoneDTO> getAllZones() { return zoneService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getZone(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse(200, "Success", zoneService.getZoneById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateZone(@PathVariable String id, @RequestBody ZoneDTO dto) {
        return ResponseEntity.ok(new ApiResponse(200, "Updated", zoneService.updateZone(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteZone(@PathVariable String id) {
        zoneService.deleteZone(id);
        return ResponseEntity.ok(new ApiResponse(200, "Deleted", null));
    }
}