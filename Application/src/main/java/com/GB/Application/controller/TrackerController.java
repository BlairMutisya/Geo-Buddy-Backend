package com.GB.Application.controller;

import com.GB.Application.dto.*;
import com.GB.Application.model.TrackerData;
import com.GB.Application.service.TrackerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trackers")
@Tag(name = "Tracker API", description = "Operations related to GF-21 trackers")
public class TrackerController {

    private final TrackerService trackerService;

    public TrackerController(TrackerService trackerService) {
        this.trackerService = trackerService;
    }


    @PostMapping("/pet")
    @Operation(summary = "Register a new pet tracker")
    public ResponseEntity<?> registerPet(@Valid @RequestBody PetDto petDto) {
        return ResponseEntity.ok(trackerService.registerPet(petDto));
    }

//    @GetMapping("/child")
    @PostMapping("/child")
    @Operation(summary = "Register a new child tracker")
    public ResponseEntity<?> registerChild(@Valid @RequestBody ChildDto childDto) {
        return ResponseEntity.ok(trackerService.registerChild(childDto));
    }

//    @GetMapping("/luggage")
    @PostMapping("/luggage")
    @Operation(summary = "Register a new luggage tracker")
    public ResponseEntity<?> registerLuggage(@Valid @RequestBody LuggageDto luggageDto) {
        return ResponseEntity.ok(trackerService.registerLuggage(luggageDto));
    }

    @GetMapping
    @Operation(summary = "Get all trackers")
    public ResponseEntity<List<?>> getAllTrackers() {
        return ResponseEntity.ok(trackerService.getAllTrackers());
    }

    @GetMapping("/{imei}")
    @Operation(summary = "Get tracker by IMEI")
    public ResponseEntity<?> getTrackerByImei(@PathVariable String imei) {
        return ResponseEntity.ok(trackerService.getTrackerByImei(imei));
    }


    @PostMapping("/update-location")
    @Operation(summary = "Update tracker location (for Postman testing)")
    public ResponseEntity<?> updateLocation(@Valid @RequestBody TrackerDataDto trackerDataDto) {
        return ResponseEntity.ok(trackerService.updateTrackerData(trackerDataDto));
    }

    // POST tracker data (already exists, but you can rename it if needed)
    @PostMapping("/data")
    @Operation(summary = "Receive tracker data from devices")
    public ResponseEntity<?> receiveTrackerData(@Valid @RequestBody TrackerDataDto trackerDataDto) {
        return ResponseEntity.ok(trackerService.updateTrackerData(trackerDataDto));
    }

    // GET all tracker data
    @GetMapping("/data")
    @Operation(summary = "Get all tracker data records")
    public ResponseEntity<List<TrackerData>> getAllTrackerData() {
        return ResponseEntity.ok(trackerService.getAllTrackerData());
    }
    @GetMapping("/data/{imei}")
    @Operation(summary = "Get tracker data by IMEI")
    public ResponseEntity<?> getTrackerDataByImei(@PathVariable String imei) {
        return trackerService.getTrackerDataByImei(imei)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}