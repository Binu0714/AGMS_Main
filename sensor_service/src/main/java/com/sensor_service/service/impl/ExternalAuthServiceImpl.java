package com.sensor_service.service.impl;

import com.sensor_service.dto.DeviceDTO;
import com.sensor_service.service.ExternalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExternalAuthServiceImpl implements ExternalAuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.iot.base-url}")
    private String baseUrl;

    @Value("${external.iot.username}")
    private String username;

    @Value("${external.iot.password}")
    private String password;

    private String accessToken;
    private String refreshToken;

    @Override
    public String getAccessToken() {
        if (accessToken == null) {
            login();
        }
        return accessToken;
    }

    private void login() {
        String loginUrl = baseUrl + "/auth/login";

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", username);
        loginRequest.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(loginRequest, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(loginUrl, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> body = response.getBody();


                if (body.containsKey("accessToken") && body.containsKey("refreshToken")) {
                    this.accessToken = (String) body.get("accessToken");
                    this.refreshToken = (String) body.get("refreshToken");
                    System.out.println("✅ Login Successful! Access & Refresh tokens saved.");
                } else {
                    System.err.println("Tokens not found in response body!");
                }
            }
        } catch (Exception e) {
            System.err.println("Login Failed: " + e.getMessage());
        }
    }

    @Override
    public DeviceDTO registerDeviceAtExternalApi(DeviceDTO deviceDTO) {
        String url = baseUrl + "/devices";
        String token = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DeviceDTO> entity = new HttpEntity<>(deviceDTO, headers);

        try {
            ResponseEntity<DeviceDTO> response = restTemplate.postForEntity(url, entity, DeviceDTO.class);
            return response.getBody();
        } catch (Exception e) {

            if (e.getMessage().contains("401")) {
                refreshAccessToken();
            }

            throw new RuntimeException("External Device Registration Failed: " + e.getMessage());
        }
    }

    @Override
    public void refreshAccessToken() {
        if (this.refreshToken == null) {
            login();
            return;
        }

        String refreshUrl = baseUrl + "/auth/refresh";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("refreshToken", this.refreshToken);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(refreshUrl, requestBody, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                this.accessToken = (String) response.getBody().get("accessToken");
                System.out.println("Access Token refreshed successfully using Refresh Token.");
            }
        } catch (Exception e) {
            System.err.println("Refreshing Token Failed, trying full login...");
            login();
        }
    }

}