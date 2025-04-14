package com.GB.Application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChildDto extends TrackerDataDto {
//    @NotBlank(message = "Child name is required")
//    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 18, message = "Age must be 18 or less")
    private Integer age;

    private String description;


    private LocalDateTime lastUpdated;
}