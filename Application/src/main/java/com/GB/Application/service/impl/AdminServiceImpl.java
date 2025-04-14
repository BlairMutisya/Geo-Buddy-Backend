package com.GB.Application.service.impl;

import com.GB.Application.exception.ImeiExistsException;
import com.GB.Application.model.*;
import com.GB.Application.repository.*;
import com.GB.Application.service.AdminService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }

    @Override
    public List<Luggage> getAllLuggage() {
        return luggageRepository.findAll();
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
    public List<TrackerData> getTrackerHistory(String imei) {
        return trackerDataRepository.findLatestByImei(imei, Pageable.unpaged());
    }
}