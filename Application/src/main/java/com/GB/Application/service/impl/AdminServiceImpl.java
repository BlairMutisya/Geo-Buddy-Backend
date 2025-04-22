package com.GB.Application.service.impl;

import com.GB.Application.exception.ImeiExistsException;
import com.GB.Application.model.*;
import com.GB.Application.repository.*;
import com.GB.Application.service.AdminService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final PetRepository petRepository;
    private final ChildRepository childRepository;
    private final LuggageRepository luggageRepository;
    private final ImeiRepository imeiRepository;
    private final TrackerDataRepository trackerDataRepository;

    public AdminServiceImpl(PetRepository petRepository,
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
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Long id, Pet updatedPet) {
        Optional<Pet> petOptional = petRepository.findById(id);
        if (petOptional.isEmpty()) throw new RuntimeException("Pet not found");
        updatedPet.setId(id);
        return petRepository.save(updatedPet);
    }

    @Override
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }

    @Override
    public Child saveChild(Child child) {
        return childRepository.save(child);
    }

    @Override
    public Child updateChild(Long id, Child updatedChild) {
        Optional<Child> childOptional = childRepository.findById(id);
        if (childOptional.isEmpty()) throw new RuntimeException("Child not found");
        updatedChild.setId(id);
        return childRepository.save(updatedChild);
    }

    @Override
    public void deleteChild(Long id) {
        childRepository.deleteById(id);
    }

    @Override
    public List<Luggage> getAllLuggage() {
        return luggageRepository.findAll();
    }

    @Override
    public Luggage saveLuggage(Luggage luggage) {
        return luggageRepository.save(luggage);
    }

    @Override
    public Luggage updateLuggage(Long id, Luggage updatedLuggage) {
        Optional<Luggage> luggageOptional = luggageRepository.findById(id);
        if (luggageOptional.isEmpty()) throw new RuntimeException("Luggage not found");
        updatedLuggage.setId(id);
        return luggageRepository.save(updatedLuggage);
    }

    @Override
    public void deleteLuggage(Long id) {
        luggageRepository.deleteById(id);
    }

    @Override
    public List<Imei> getAllImeiNumbers() {
        return imeiRepository.findAll();
    }

    @Override
    public Imei addImei(String imei) {
        if (imeiRepository.existsByImei(imei)) {
            throw new ImeiExistsException("IMEI already exists in database");
        }

        Imei newImei = new Imei();
        newImei.setImei(imei);
        newImei.setRegistered(false);

        return imeiRepository.save(newImei);
    }

    @Override
    public Pet addPet(Pet pet) {
        return null;
    }

    @Override
    public Child addChild(Child child) {
        return null;
    }

    @Override
    public Luggage addLuggage(Luggage luggage) {
        return null;
    }

    @Override
    public Imei updateImei(Long id, Imei updatedImei) {
        Optional<Imei> imeiOptional = imeiRepository.findById(id);
        if (imeiOptional.isEmpty()) throw new RuntimeException("IMEI not found");
        updatedImei.setId(id);
        return imeiRepository.save(updatedImei);
    }

    @Override
    public void deleteImei(Long id) {
        imeiRepository.deleteById(id);
    }

    @Override
    public List<TrackerData> getTrackerHistory(String imei) {
        return trackerDataRepository.findLatestByImei(imei, Pageable.unpaged());
    }
}
