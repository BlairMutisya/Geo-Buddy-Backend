package com.GB.Application.controller;

import com.GB.Application.model.GF21Entity;
import com.GB.Application.service.GF21Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gf21")
public class GF21Controller {

    @Autowired
    private GF21Service gf21Service;

    @GetMapping("/data/{deviceId}")
    public List<GF21Entity> getDataByDeviceId(@PathVariable String deviceId) {
        return gf21Service.getDataByDeviceId(deviceId);
    }

    @GetMapping("/data")
    public List<GF21Entity> getAllData() {
        return gf21Service.getAllData();
    }
}