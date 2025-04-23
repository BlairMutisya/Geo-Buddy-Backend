package com.GB.Application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnifiedTrackerDto {
    private String imei;
    private String trackerName;
    private String type;
    private Double latitude;
    private Double longitude;
    private String description;
    private String status;
    private Double batteryCapacity;
    private LocalDateTime timestamp;
    private Integer age;
    private String breed;
    private String luggageType;
    private String color;
}
