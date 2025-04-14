package com.GB.Application.repository;

import com.GB.Application.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findByImei(String imei);
    boolean existsByImei(String imei);
}