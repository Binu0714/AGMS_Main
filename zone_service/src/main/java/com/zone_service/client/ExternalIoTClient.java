package com.zone_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Map;

@FeignClient(name = "external-iot", url = "http://104.211.95.241:8080/api")
public interface ExternalIoTClient {

    @PostMapping("/devices")
    Map<String, Object> registerDevice(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> payload
    );
}