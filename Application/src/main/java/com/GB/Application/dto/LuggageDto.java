package com.GB.Application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LuggageDto extends TrackerDataDto {
    @NotBlank(message = "Luggage type is required")
    private String luggageType;

    @NotBlank(message = "Color is required")
    private String color;

    private String description;
}