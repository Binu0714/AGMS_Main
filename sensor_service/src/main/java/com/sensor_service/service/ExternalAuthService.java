package com.sensor_service.service;

import com.sensor_service.dto.DeviceDTO;

public interface ExternalAuthService {
    DeviceDTO registerDeviceAtExternalApi(DeviceDTO deviceDTO);
    String getAccessToken();
    void refreshAccessToken();
}
