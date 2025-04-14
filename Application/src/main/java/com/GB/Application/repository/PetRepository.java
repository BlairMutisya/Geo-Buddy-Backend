package com.GB.Application.repository;

import com.GB.Application.model.Luggage;
import com.GB.Application.model.Pet;
import com.GB.Application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByImei(String imei);
    boolean existsByImei(String imei);
    List<Pet> findByUser(User user);
}