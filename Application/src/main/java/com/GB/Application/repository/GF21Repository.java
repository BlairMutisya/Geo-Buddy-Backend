package com.GB.Application.repository;

import com.GB.Application.model.GF21Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GF21Repository extends JpaRepository<GF21Entity, Long> {
    List<GF21Entity> findByDeviceId(String deviceId);
}