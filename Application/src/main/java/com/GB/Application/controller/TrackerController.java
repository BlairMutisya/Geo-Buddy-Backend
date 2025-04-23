package com.GB.Application.controller;

import com.GB.Application.dto.*;
import com.GB.Application.model.TrackerData;
import com.GB.Application.service.TrackerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/trackers")
@Tag(name = "Tracker API", description = "Operations related to GF-21 trackers")
public class TrackerController {

    private final TrackerService trackerService;

    public TrackerController(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @PostMapping("/pet")
    @Operation(summary = "Register a new pet tracker")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> registerPet(@Valid @RequestBody PetDto petDto) {
        return ResponseEntity.ok(trackerService.registerPet(petDto));
    }

    @PostMapping("/child")
    @Operation(summary = "Register a new child tracker")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> registerChild(@Valid @RequestBody ChildDto childDto) {
        return ResponseEntity.ok(trackerService.registerChild(childDto));
    }

    @PostMapping("/luggage")
    @Operation(summary = "Register a new luggage tracker")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> registerLuggage(@Valid @RequestBody LuggageDto luggageDto) {
        return ResponseEntity.ok(trackerService.registerLuggage(luggageDto));
    }

//    @GetMapping("/all-trackers")
//    @Operation(summary = "Get all trackers (Admin only)")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<?>> getAllTrackers() {
//        return ResponseEntity.ok(trackerService.getAllTrackers());
//    }

    @GetMapping("/my")
    @Operation(summary = "Get trackers registered by the current user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<UnifiedTrackerDto>> getMyTrackers() {
        return ResponseEntity.ok(trackerService.getMyTrackers());
    }

    @GetMapping("/{imei}")
    @Operation(summary = "Get tracker by IMEI")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getTrackerByImei(@PathVariable String imei) {
        return ResponseEntity.ok(trackerService.getTrackerByImei(imei));
    }

    @PostMapping("/update-location")
    @Operation(summary = "Update tracker location (for Postman testing)")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateLocation(@Valid @RequestBody TrackerDataDto trackerDataDto) {
        return ResponseEntity.ok(trackerService.updateTrackerData(trackerDataDto));
    }

    @PostMapping("/data")
    @Operation(summary = "Receive tracker data from devices")
    public ResponseEntity<?> receiveTrackerData(@Valid @RequestBody TrackerDataDto trackerDataDto) {
        return ResponseEntity.ok(trackerService.updateTrackerData(trackerDataDto));
    }

    @GetMapping("/data")
    @Operation(summary = "Get all tracker data records")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TrackerData>> getAllTrackerData() {
        return ResponseEntity.ok(trackerService.getAllTrackerData());
    }

    @GetMapping("/data/{imei}")
    @Operation(summary = "Get tracker data by IMEI")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getTrackerDataByImei(@PathVariable String imei) {
        return trackerService.getTrackerDataByImei(imei)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
