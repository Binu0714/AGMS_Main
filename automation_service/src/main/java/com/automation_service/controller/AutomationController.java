package com.automation_service.controller;

import com.automation_service.dto.TelemetryData;
import com.automation_service.entity.AutomationLog;
import com.automation_service.service.AutomationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {

    @Autowired
    private AutomationService automationService;

    // Internal endpoint called by Sensor Service
    @PostMapping("/process")
    public void process(@RequestBody TelemetryData data) {
        automationService.processTelemetry(data);
    }

    // Public endpoint for farmers to see their specific logs
    @GetMapping("/logs/{userId}")
    public ResponseEntity<List<AutomationLog>> getLogs(@PathVariable String userId) {
        return ResponseEntity.ok(automationService.findAllLogs(userId));
    }
}
