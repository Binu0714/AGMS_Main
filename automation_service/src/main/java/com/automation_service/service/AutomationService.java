package com.automation_service.service;

import com.automation_service.dto.TelemetryData;
import com.automation_service.entity.AutomationLog;

import java.util.List;

public interface AutomationService {
    void processTelemetry(TelemetryData data);
    List<AutomationLog> findAllLogs(String userId);
}
