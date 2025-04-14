package com.GB.Application.repository;

import com.GB.Application.model.Luggage;
import com.GB.Application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface LuggageRepository extends JpaRepository<Luggage, Long> {
    Optional<Luggage> findByImei(String imei);
    boolean existsByImei(String imei);
    List<Luggage> findByUser(User user);
}