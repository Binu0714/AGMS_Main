package com.crop_service.repo;

import com.crop_service.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRepo extends JpaRepository<Crop, String> {
    List<Crop> findByUserId(String userId);
}