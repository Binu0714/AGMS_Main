package com.automation_service.repo;

import com.automation_service.entity.AutomationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomationRepo extends JpaRepository<AutomationLog, Long> {
    // Custom method used in your ServiceImpl
    List<AutomationLog> findByUserId(String userId);
}
