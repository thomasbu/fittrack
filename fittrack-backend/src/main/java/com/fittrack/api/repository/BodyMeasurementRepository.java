package com.fittrack.api.repository;

import com.fittrack.api.model.BodyMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BodyMeasurementRepository extends JpaRepository<BodyMeasurement, Long> {
    List<BodyMeasurement> findByUserId(Long userId);
}