package com.GB.Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DeviceNotFoundException extends TrackerException {
    public DeviceNotFoundException(String imei) {
        super("DEVICE_NOT_FOUND",
                String.format("Tracker device with IMEI %s not found", imei));
    }


}