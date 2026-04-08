package com.zone_service.service.impl;

import com.zone_service.client.ExternalIoTClient;
import com.zone_service.dto.ZoneDTO;
import com.zone_service.entity.Zone;
import com.zone_service.repo.ZoneRepo;
import com.zone_service.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired private ZoneRepo zoneRepo;
    @Autowired private ExternalIoTClient iotClient;

    @Override
    public ZoneDTO saveZone(ZoneDTO dto, String token) {
        // 1. Validation logic
        if (dto.getMinTemp() >= dto.getMaxTemp()) {
            throw new RuntimeException("minTemp must be strictly less than maxTemp");
        }

        String extDeviceId;

        try {
            // 2. Try to register on the REAL External IoT Provider
            Map<String, String> payload = new HashMap<>();
            payload.put("name", dto.getName() + "-Sensor");
            payload.put("zoneId", dto.getName());

            Map<String, Object> response = iotClient.registerDevice(token, payload);
            extDeviceId = response.get("deviceId").toString();
            System.out.println("SUCCESS: Registered on External API");

        } catch (Exception e) {
            // 3. FALLBACK: If the external server is down, generate a MOCK Device ID
            System.err.println("EXTERNAL API ERROR: " + e.getMessage());
            System.out.println("Switching to MOCK Device ID for testing...");
            extDeviceId = "MOCK-DEVICE-" + UUID.randomUUID().toString();
        }

        // 4. Save to your local MySQL database
        Zone zone = new Zone(UUID.randomUUID().toString(), dto.getName(),
                dto.getMinTemp(), dto.getMaxTemp(), extDeviceId, dto.getUserId());

        zoneRepo.save(zone);

        // 5. Prepare Response
        dto.setId(zone.getId());
        dto.setDeviceId(extDeviceId);
        return dto;
    }

    @Override
    public List<ZoneDTO> findAll() {
        return zoneRepo.findAll().stream().map(z -> new ZoneDTO(z.getId(), z.getName(),
                z.getMinTemp(), z.getMaxTemp(), z.getDeviceId(), z.getUserId())).collect(Collectors.toList());
    }

    @Override
    public ZoneDTO getZoneById(String id) {
        Zone z = zoneRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        return new ZoneDTO(z.getId(), z.getName(), z.getMinTemp(), z.getMaxTemp(), z.getDeviceId(), z.getUserId());
    }

    @Override
    public ZoneDTO updateZone(String id, ZoneDTO dto) {
        Zone z = zoneRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        z.setName(dto.getName());
        z.setMinTemp(dto.getMinTemp());
        z.setMaxTemp(dto.getMaxTemp());
        zoneRepo.save(z);
        return dto;
    }

    @Override
    public void deleteZone(String id) { zoneRepo.deleteById(id); }
}