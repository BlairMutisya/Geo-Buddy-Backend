package com.GB.Application.service;

import com.GB.Application.dto.*;
import com.GB.Application.model.*;

import java.util.List;
import java.util.Optional;

public interface TrackerService {
    // Pet operations
    Pet registerPet(PetDto petDto);
//    Pet updatePetLocation(String imei, Double latitude, Double longitude);

    // Child operations
    Child registerChild(ChildDto childDto);
//    Child updateChildLocation(String imei, Double latitude, Double longitude);

    // Luggage operations
    Luggage registerLuggage(LuggageDto luggageDto);
//    Luggage updateLuggageLocation(String imei, Double latitude, Double longitude);

    // Common operations
    TrackerData updateTrackerData(TrackerDataDto trackerDataDto);
//    List<? extends Tracker> getAllTrackers();
    List<UnifiedTrackerDto> getAllTrackers();
    List<TrackerData> getAllTrackerData();


    Tracker getTrackerByImei(String imei);

    // IMEI operations
    boolean isImeiRegistered(String imei);
//    boolean isImeiValid(String imei);
     Optional<TrackerData> getTrackerDataByImei(String imei);
     List<UnifiedTrackerDto> getMyTrackers();
//    List<UnifiedTrackerDto> getAllTrackers(); // for admins
//    List<UnifiedTrackerDto> getMyTrackers();  // for current user

}