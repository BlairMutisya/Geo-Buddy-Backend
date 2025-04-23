package com.GB.Application.service.impl;

import com.GB.Application.dto.*;
import com.GB.Application.exception.*;
import com.GB.Application.model.*;
import com.GB.Application.repository.*;
import com.GB.Application.service.TrackerService;
import com.GB.Application.service.CurrentUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final CurrentUserService currentUserService;

    public TrackerServiceImpl(PetRepository petRepository,
                              ChildRepository childRepository,
                              LuggageRepository luggageRepository,
                              ImeiRepository imeiRepository,
                              TrackerDataRepository trackerDataRepository,
                              CurrentUserService currentUserService) {
        this.petRepository = petRepository;
        this.childRepository = childRepository;
        this.luggageRepository = luggageRepository;
        this.imeiRepository = imeiRepository;
        this.trackerDataRepository = trackerDataRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public Pet registerPet(PetDto petDto) {
        validateImeiForRegistration(petDto.getImei());

        Pet pet = new Pet();
        mapCommonTrackerFields(pet, petDto);
        pet.setBreed(petDto.getBreed());
        pet.setAge(petDto.getAge());
        pet.setDescription(petDto.getDescription());

        // Associate the pet with the current user
        pet.setUser(currentUserService.getCurrentUser());

        markImeiAsRegistered(petDto.getImei());
        return petRepository.save(pet);
    }

    @Override
    public Child registerChild(ChildDto childDto) {
        validateImeiForRegistration(childDto.getImei());

        Child child = new Child();
        mapCommonTrackerFields(child, childDto);
        child.setAge(childDto.getAge());
        child.setDescription(childDto.getDescription());

        // Associate the child with the current user
        child.setUser(currentUserService.getCurrentUser());

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

        // Associate the luggage with the current user
        luggage.setUser(currentUserService.getCurrentUser());

        markImeiAsRegistered(luggageDto.getImei());
        return luggageRepository.save(luggage);
    }

    @Override
    public TrackerData updateTrackerData(TrackerDataDto trackerDataDto) {
        Optional<TrackerData> existingDataOpt = trackerDataRepository.findByImei(trackerDataDto.getImei());

        TrackerData data = existingDataOpt.orElseGet(TrackerData::new);
        data.setImei(trackerDataDto.getImei());
        data.setLatitude(trackerDataDto.getLatitude());
        data.setLongitude(trackerDataDto.getLongitude());
        data.setBatteryCapacity(trackerDataDto.getBatteryCapacity());
        data.setStatus(trackerDataDto.getStatus());
        data.setTimestamp(LocalDateTime.now());

        markImeiAsRegistered(trackerDataDto.getImei());

        return trackerDataRepository.save(data);
    }


    @Override
    public List<UnifiedTrackerDto> getMyTrackers() {
        User currentUser = currentUserService.getCurrentUser();
        List<UnifiedTrackerDto> userTrackers = new ArrayList<>();

        petRepository.findByUser(currentUser).forEach(pet -> {
            UnifiedTrackerDto dto = mapPetToDto(pet);
            userTrackers.add(dto);
        });

        childRepository.findByUser(currentUser).forEach(child -> {
            UnifiedTrackerDto dto = mapChildToDto(child);
            userTrackers.add(dto);
        });

        luggageRepository.findByUser(currentUser).forEach(luggage -> {
            UnifiedTrackerDto dto = mapLuggageToDto(luggage);
            userTrackers.add(dto);
        });

        return userTrackers;
    }
    @Override
    public List<UnifiedTrackerDto> getAllTrackers() {
        List<UnifiedTrackerDto> allTrackers = new ArrayList<>();

        petRepository.findAll().forEach(pet -> {
            UnifiedTrackerDto dto = mapPetToDto(pet);
            allTrackers.add(dto);
        });

        childRepository.findAll().forEach(child -> {
            UnifiedTrackerDto dto = mapChildToDto(child);
            allTrackers.add(dto);
        });

        luggageRepository.findAll().forEach(luggage -> {
            UnifiedTrackerDto dto = mapLuggageToDto(luggage);
            allTrackers.add(dto);
        });

        return allTrackers;
    }

    private UnifiedTrackerDto mapPetToDto(Pet pet) {
        UnifiedTrackerDto dto = new UnifiedTrackerDto();
        dto.setImei(pet.getImei());
        dto.setTrackerName(pet.getTrackerName());
        dto.setType("pet");
        dto.setLatitude(pet.getLatitude());
        dto.setLongitude(pet.getLongitude());
        dto.setDescription(pet.getDescription());
        dto.setStatus(pet.getStatus());
        dto.setBatteryCapacity(pet.getBatteryCapacity());
        dto.setTimestamp(pet.getLastUpdated());
        dto.setAge(pet.getAge());
        dto.setBreed(pet.getBreed());
        return dto;
    }

    private UnifiedTrackerDto mapChildToDto(Child child) {
        UnifiedTrackerDto dto = new UnifiedTrackerDto();
        dto.setImei(child.getImei());
        dto.setTrackerName(child.getTrackerName());
        dto.setType("child");
        dto.setLatitude(child.getLatitude());
        dto.setLongitude(child.getLongitude());
        dto.setDescription(child.getDescription());
        dto.setStatus(child.getStatus());
        dto.setBatteryCapacity(child.getBatteryCapacity());
        dto.setTimestamp(child.getLastUpdated());
        dto.setAge(child.getAge());
        return dto;
    }

    private UnifiedTrackerDto mapLuggageToDto(Luggage luggage) {
        UnifiedTrackerDto dto = new UnifiedTrackerDto();
        dto.setImei(luggage.getImei());
        dto.setTrackerName(luggage.getTrackerName());
        dto.setType("luggage");
        dto.setLatitude(luggage.getLatitude());
        dto.setLongitude(luggage.getLongitude());
        dto.setDescription(luggage.getDescription());
        dto.setStatus(luggage.getStatus());
        dto.setBatteryCapacity(luggage.getBatteryCapacity());
        dto.setTimestamp(luggage.getLastUpdated());
        dto.setLuggageType(luggage.getLuggageType());
        dto.setColor(luggage.getColor());
        return dto;
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
    @Override
    public List<TrackerData> getAllTrackerData() {
        return trackerDataRepository.findAll();
    }
    @Override
    public Optional<TrackerData> getTrackerDataByImei(String imei) {
        return trackerDataRepository.findByImei(imei);
    }

}
