package com.sensor_service.task;

import com.sensor_service.client.AutomationClient;
import com.sensor_service.client.ZoneClient;
import com.sensor_service.dto.ApiResponse;
import com.sensor_service.dto.TelemetryData;
import com.sensor_service.dto.TelemetryValue;
import com.sensor_service.service.impl.ExternalAuthServiceImpl;
import com.sensor_service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Component
public class TelemetryFetcher {

    @Autowired
    private ZoneClient zoneClient;

    @Autowired
    private ExternalAuthServiceImpl authService;

    @Autowired
    private AutomationClient automationClient;

    @Value("${external.iot.base-url:http://104.211.95.241:8080/api}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000)
    public void fetch() {
        System.out.println("--- Starting Telemetry Cycle ---");

        ApiResponse zoneResponse = zoneClient.getAllZones();
        if (zoneResponse == null || zoneResponse.getData() == null) return;

        List<Map<String, Object>> zones = (List<Map<String, Object>>) zoneResponse.getData();
        String token = authService.getAccessToken();

        for (Map<String, Object> zone : zones) {
            String deviceId = (String) zone.get("deviceId");
            String zoneId = String.valueOf(zone.get("id"));

            if (deviceId == null) continue;

            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                String url = baseUrl + "/devices/telemetry/" + deviceId;
                ResponseEntity<TelemetryData> response = restTemplate.exchange(url, HttpMethod.GET, entity, TelemetryData.class);

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    TelemetryData realData = response.getBody();
                    realData.setZoneId(zoneId);
                    automationClient.sendToAutomation(realData);
                    System.out.println("SUCCESS: Real Data fetched for Zone " + zoneId);
                    continue;
                }
            } catch (Exception e) {
                System.err.println("External Server Down for device " + deviceId + ". Switching to Mock.");
            }

            sendMockData(zoneId, deviceId);
        }
    }

    private void sendMockData(String zoneId, String deviceId) {
        TelemetryData mockData = new TelemetryData();
        TelemetryValue val = new TelemetryValue();
        val.setTemperature(22 + (Math.random() * 8)); // 22 to 30 degrees
        val.setHumidity(55.0 + (Math.random() * 10));
        mockData.setValue(val);
        mockData.setZoneId(zoneId);
        mockData.setDeviceId(deviceId);
        automationClient.sendToAutomation(mockData);
        System.out.println("MOCKED: Data sent for Zone " + zoneId);
    }
}