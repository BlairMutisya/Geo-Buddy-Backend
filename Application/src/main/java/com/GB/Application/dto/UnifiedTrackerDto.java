package com.GB.Application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnifiedTrackerDto {
    private String imei;
    private String trackerName;
    private String type; // "pet", "child", "luggage"
    private Double latitude;
    private Double longitude;
    private String description;
    private String status;
}
