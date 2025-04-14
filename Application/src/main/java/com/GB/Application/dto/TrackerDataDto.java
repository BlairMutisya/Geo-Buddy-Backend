package com.GB.Application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackerDataDto {
    @NotBlank(message = "Tracker name is required")
    @Size(max = 100, message = "Tracker name must be less than 100 characters")
    private String trackerName;

    @NotBlank(message = "IMEI is required")
    @Size(min = 15, max = 15, message = "IMEI must be 15 digits")
    private String imei;

    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private Double longitude;

    @NotNull(message = "Battery capacity is required")
    @DecimalMin(value = "0.0", message = "Battery capacity cannot be negative")
    @DecimalMax(value = "100.0", message = "Battery capacity cannot exceed 100%")
    private Double batteryCapacity;

    @NotBlank(message = "Status is required")
    private String status; // active/inactive

    private LocalDateTime timestamp = LocalDateTime.now();


}