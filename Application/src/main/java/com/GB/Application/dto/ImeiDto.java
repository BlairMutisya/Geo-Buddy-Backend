package com.GB.Application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ImeiDto {
    // Single IMEI fields
    @NotBlank(message = "IMEI is required")
    @Size(min = 15, max = 15, message = "IMEI must be 15 digits")
    @Pattern(regexp = "^[0-9]*$", message = "IMEI must contain only digits")
    private String imei;

    private Boolean registered = false;

    // For single IMEI requests (POST /imei)
    public static class ImeiRequest {
        @NotBlank(message = "IMEI is required")
        @Size(min = 15, max = 15, message = "IMEI must be exactly 15 digits")
        @Pattern(regexp = "^[0-9]*$", message = "IMEI must contain only digits")
        private String imei;

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }
    }

}