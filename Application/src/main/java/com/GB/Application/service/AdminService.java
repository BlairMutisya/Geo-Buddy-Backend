package com.GB.Application.service;

import com.GB.Application.model.*;

import java.util.List;

public interface AdminService {
    // ----- GET -----
    List<Pet> getAllPets();
    List<Child> getAllChildren();
    List<Luggage> getAllLuggage();
    List<Imei> getAllImeiNumbers();
    List<TrackerData> getTrackerHistory(String imei);

    // ----- POST / CREATE -----
    Imei addImei(String imei);
//    Pet addPet(Pet pet);
//    Child addChild(Child child);
//    Luggage addLuggage(Luggage luggage);

    // ----- PUT / UPDATE -----
    Pet updatePet(Long id, Pet updatedPet);
    Child updateChild(Long id, Child updatedChild);
    Luggage updateLuggage(Long id, Luggage updatedLuggage);

    Pet addPet(Pet pet);

    Child addChild(Child child);

    Luggage addLuggage(Luggage luggage);

    Imei updateImei(Long id, Imei updatedImei);

    // ----- DELETE -----
    void deletePet(Long id);
    void deleteChild(Long id);
    void deleteLuggage(Long id);
    void deleteImei(Long id);

    Luggage saveLuggage(Luggage luggage);

    Child saveChild(Child child);

    Pet savePet(Pet pet);
}
