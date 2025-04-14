package com.GB.Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class LocationServiceException extends TrackerException {
    public LocationServiceException(String imei) {
        super("LOCATION_SERVICE_ERROR",
                String.format("Failed to retrieve location for device %s", imei));
    }

    public LocationServiceException(String imei, Throwable cause) {
        super("LOCATION_SERVICE_ERROR",
                String.format("Location service failed for device %s: %s",
                        imei, cause.getMessage()));
    }
}