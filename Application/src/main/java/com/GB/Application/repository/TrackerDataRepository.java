package com.GB.Application.repository;

import com.GB.Application.model.TrackerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrackerDataRepository extends JpaRepository<TrackerData, Long> {


    // Find latest data for a specific IMEI
    @Query("SELECT t FROM TrackerData t WHERE t.imei = ?1 ORDER BY t.timestamp DESC")
    List<TrackerData> findLatestByImei(String imei, org.springframework.data.domain.Pageable pageable);
    Optional<TrackerData> findByImei(String imei);
}