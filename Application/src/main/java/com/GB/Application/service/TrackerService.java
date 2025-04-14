package com.GB.Application.service;

import com.GB.Application.dto.*;
import com.GB.Application.model.*;

import java.util.List;

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
    List<? extends Tracker> getAllTrackers();
    Tracker getTrackerByImei(String imei);

    // IMEI operations
    boolean isImeiRegistered(String imei);
//    boolean isImeiValid(String imei);
}