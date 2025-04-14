package com.GB.Application.repository;

import com.GB.Application.model.Child;
import com.GB.Application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findByImei(String imei);
    boolean existsByImei(String imei);
    List<Child> findByUser(User user);
}