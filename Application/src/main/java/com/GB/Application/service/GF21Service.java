package com.GB.Application.service;

import com.GB.Application.model.GF21Entity;
import com.GB.Application.model.GF21Message;
import com.GB.Application.repository.GF21Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GF21Service {

    @Autowired
    private GF21Repository repository;

    public void processMessage(GF21Message message) {
        GF21Entity entity = new GF21Entity(
                message.getDeviceId(),
                LocalDateTime.parse(message.getTimestamp()),
                message.getLatitude(),
                message.getLongitude()
        );
        repository.save(entity);
    }

    public List<GF21Entity> getDataByDeviceId(String deviceId) {
        return repository.findByDeviceId(deviceId);
    }

    public List<GF21Entity> getAllData() {
        return repository.findAll();
    }
}