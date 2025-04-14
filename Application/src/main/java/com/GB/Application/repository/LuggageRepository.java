package com.GB.Application.repository;

import com.GB.Application.model.Luggage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LuggageRepository extends JpaRepository<Luggage, Long> {
    Optional<Luggage> findByImei(String imei);
    boolean existsByImei(String imei);
}