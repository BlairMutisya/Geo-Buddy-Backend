package com.GB.Application.repository;

import com.GB.Application.model.Imei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImeiRepository extends JpaRepository<Imei, Long> {

    // Find IMEI by number
    Optional<Imei> findByImei(String imei);

    // Check if IMEI exists
    boolean existsByImei(String imei);

    // Find all registered/unregistered IMEIs
    List<Imei> findByRegistered(boolean registered);

    // Count registered devices
    long countByRegistered(boolean registered);

    // Find IMEIs that are not yet registered to any device
//    @Query("SELECT i FROM Imei i WHERE i.registered = false")
//    List<Imei> findAvailableImeis();
//
//    // Bulk update registration status
//    @Modifying
//    @Query("UPDATE Imei i SET i.registered = :registered WHERE i.imei IN :imeis")
//    int updateRegistrationStatus(@Param("registered") boolean registered,
//                                 @Param("imeis") List<String> imeis);
}