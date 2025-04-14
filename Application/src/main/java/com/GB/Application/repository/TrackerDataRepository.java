package com.GB.Application.repository;

import com.GB.Application.model.TrackerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrackerDataRepository extends JpaRepository<TrackerData, Long> {

    // Find all data for a specific IMEI
    List<TrackerData> findByImei(String imei);

    // Find latest data for a specific IMEI
    @Query("SELECT t FROM TrackerData t WHERE t.imei = ?1 ORDER BY t.timestamp DESC")
    List<TrackerData> findLatestByImei(String imei, org.springframework.data.domain.Pageable pageable);

    // Find data within a time range
    List<TrackerData> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    // Find data by IMEI and time range
    List<TrackerData> findByImeiAndTimestampBetween(String imei, LocalDateTime start, LocalDateTime end);

    // Find data by battery level
    List<TrackerData> findByBatteryCapacityLessThan(double batteryLevel);

    // Find data by status
    List<TrackerData> findByStatus(String status);

    // Check if data exists for IMEI
    boolean existsByImei(String imei);

    // Count entries by IMEI
    long countByImei(String imei);

    // Delete all data for an IMEI
    void deleteByImei(String imei);

    // Find all active trackers (with data in last X minutes)
    @Query("SELECT DISTINCT t.imei FROM TrackerData t WHERE t.timestamp > ?1")
    List<String> findActiveImeis(LocalDateTime cutoffTime);
}