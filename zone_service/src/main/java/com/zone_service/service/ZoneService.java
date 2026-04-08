package com.zone_service.service;

import com.zone_service.dto.ZoneDTO;
import java.util.List;

public interface ZoneService {
    ZoneDTO saveZone(ZoneDTO zoneDTO, String token);
    List<ZoneDTO> findAll();
    ZoneDTO getZoneById(String id);
    ZoneDTO updateZone(String id, ZoneDTO zoneDTO);
    void deleteZone(String id);
}