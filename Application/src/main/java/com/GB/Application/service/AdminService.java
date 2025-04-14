package com.GB.Application.service;

import com.GB.Application.model.*;

import java.util.List;

public interface AdminService {
    List<Pet> getAllPets();
    List<Child> getAllChildren();
    List<Luggage> getAllLuggage();
    List<Imei> getAllImeiNumbers();
    Imei addImei(String imei);
    List<TrackerData> getTrackerHistory(String imei);
}