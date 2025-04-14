package com.GB.Application.service.impl;

import com.GB.Application.dto.*;
import com.GB.Application.exception.*;
import com.GB.Application.model.*;
import com.GB.Application.repository.*;
import com.GB.Application.service.TrackerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrackerServiceImpl implements TrackerService {

    private final PetRepository petRepository;
    private final ChildRepository childRepository;
    private final LuggageRepository luggageRepository;
    private final ImeiRepository imeiRepository;
    private final TrackerDataRepository trackerDataRepository;

    public TrackerServiceImpl(PetRepository petRepository,
                              ChildRepository childRepository,
                              LuggageRepository luggageRepository,
                              ImeiRepository imeiRepository,
                              TrackerDataRepository trackerDataRepository) {
        this.petRepository = petRepository;
        this.childRepository = childRepository;
        this.luggageRepository = luggageRepository;
        this.imeiRepository = imeiRepository;
        this.trackerDataRepository = trackerDataRepository;
    }

    @Override
    public Pet registerPet(PetDto petDto) {
        validateImeiForRegistration(petDto.getImei());

        Pet pet = new Pet();
        mapCommonTrackerFields(pet, petDto);
        pet.setBreed(petDto.getBreed());
        pet.setAge(petDto.getAge());
        pet.setDescription(petDto.getDescription());

        markImeiAsRegistered(petDto.getImei());
        return petRepository.save(pet);
    }

    @Override
    public Child registerChild(ChildDto childDto) {
        validateImeiForRegistration(childDto.getImei());

        Child child = new Child();
        mapCommonTrackerFields(child, childDto);
//        child.setName(childDto.getName());
        child.setAge(childDto.getAge());
        child.setDescription(childDto.getDescription());
//        child.setGuardianName(childDto.getGuardianName());

        markImeiAsRegistered(childDto.getImei());
        return childRepository.save(child);
    }

    @Override
    public Luggage registerLuggage(LuggageDto luggageDto) {
        validateImeiForRegistration(luggageDto.getImei());

        Luggage luggage = new Luggage();
        mapCommonTrackerFields(luggage, luggageDto);
        luggage.setLuggageType(luggageDto.getLuggageType());
        luggage.setColor(luggageDto.getColor());
        luggage.setDescription(luggageDto.getDescription());
//        luggage.setOwnerName(luggageDto.getOwnerName());

        markImeiAsRegistered(luggageDto.getImei());
        return luggageRepository.save(luggage);
    }

    @Override
    public TrackerData updateTrackerData(TrackerDataDto trackerDataDto) {
        TrackerData data = new TrackerData();
        data.setImei(trackerDataDto.getImei());
        data.setLatitude(trackerDataDto.getLatitude());
        data.setLongitude(trackerDataDto.getLongitude());
        data.setBatteryCapacity(trackerDataDto.getBatteryCapacity());
        data.setStatus(trackerDataDto.getStatus());
        data.setTimestamp(LocalDateTime.now());


        return trackerDataRepository.save(data);
    }

    @Override
    public List<? extends Tracker> getAllTrackers() {
        throw new UnsupportedOperationException("Use specific endpoints for each tracker type");
    }

    @Override
    public Tracker getTrackerByImei(String imei) {
        Optional<Pet> pet = petRepository.findByImei(imei);
        if (pet.isPresent()) return pet.get();

        Optional<Child> child = childRepository.findByImei(imei);
        if (child.isPresent()) return child.get();

        Optional<Luggage> luggage = luggageRepository.findByImei(imei);
        if (luggage.isPresent()) return luggage.get();

        throw new DeviceNotFoundException("No tracker found with IMEI: " + imei);
    }

    @Override
    public boolean isImeiRegistered(String imei) {
        return petRepository.existsByImei(imei) ||
                childRepository.existsByImei(imei) ||
                luggageRepository.existsByImei(imei);
    }

//    @Override
//    public boolean isImeiValid(String imei) {
//        return imeiRepository.existsByImei(imei);
//    }

//    @Override
//    public Pet updatePetLocation(String imei, Double latitude, Double longitude) {
//        Pet pet = petRepository.findByImei(imei)
//                .orElseThrow(() -> new DeviceNotFoundException("Pet tracker not found"));
//
//        pet.setLatitude(latitude);
//        pet.setLongitude(longitude);
//        pet.setLastUpdated(LocalDateTime.now());
//
//        saveTrackerData(pet);
//        return petRepository.save(pet);
//    }

//    @Override
//    public Child updateChildLocation(String imei, Double latitude, Double longitude) {
//        Child child = childRepository.findByImei(imei)
//                .orElseThrow(() -> new DeviceNotFoundException("Child tracker not found"));
//
//        child.setLatitude(latitude);
//        child.setLongitude(longitude);
//        child.setLastUpdated(LocalDateTime.now());
//
//        saveTrackerData(child);
//        return childRepository.save(child);
//    }

//    @Override
//    public Luggage updateLuggageLocation(String imei, Double latitude, Double longitude) {
//        Luggage luggage = luggageRepository.findByImei(imei)
//                .orElseThrow(() -> new DeviceNotFoundException("Luggage tracker not found"));
//
//        luggage.setLatitude(latitude);
//        luggage.setLongitude(longitude);
//        luggage.setLastUpdated(LocalDateTime.now());
//
//        saveTrackerData(luggage);
//        return luggageRepository.save(luggage);
//    }

    // Helper methods
    private void validateImeiForRegistration(String imei) {
        if (!imeiRepository.existsByImei(imei)) {
            throw new ImeiNotFoundException("IMEI not found in database: " + imei);
        }
        if (isImeiRegistered(imei)) {
            throw new ImeiExistsException("IMEI already registered: " + imei);
        }
    }

    private void markImeiAsRegistered(String imei) {
        imeiRepository.findByImei(imei).ifPresent(imeiRecord -> {
            imeiRecord.setRegistered(true);
            imeiRepository.save(imeiRecord);
        });
    }

    private void mapCommonTrackerFields(Tracker tracker, TrackerDataDto dto) {
        tracker.setTrackerName(dto.getTrackerName());
        tracker.setImei(dto.getImei());
        tracker.setBatteryCapacity(dto.getBatteryCapacity());
        tracker.setLatitude(dto.getLatitude());
        tracker.setLongitude(dto.getLongitude());
        tracker.setStatus(dto.getStatus());
        tracker.setLastUpdated(LocalDateTime.now());
    }

//    private void saveTrackerData(Tracker tracker) {
//        TrackerData data = new TrackerData();
//        data.setImei(tracker.getImei());
//        data.setLatitude(tracker.getLatitude());
//        data.setLongitude(tracker.getLongitude());
//        data.setBatteryCapacity(tracker.getBatteryCapacity());
//        data.setStatus(tracker.getStatus());
//        data.setTimestamp(LocalDateTime.now());
//        trackerDataRepository.save(data);
//    }
}