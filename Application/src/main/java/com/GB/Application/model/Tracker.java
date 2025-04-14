package com.GB.Application.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class Tracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String trackerName;
    private String imei;
    private double batteryCapacity;
    private double latitude;
    private double longitude;
    private String status; // active/inactive

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}