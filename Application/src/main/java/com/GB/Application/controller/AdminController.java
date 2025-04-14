package com.GB.Application.controller;

import com.GB.Application.dto.ImeiDto;
import com.GB.Application.model.*;
import com.GB.Application.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin API", description = "Administrative operations")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/pets")
    @Operation(summary = "Get all pet trackers")
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(adminService.getAllPets());
    }

    @GetMapping("/children")
    @Operation(summary = "Get all child trackers")
    public ResponseEntity<List<Child>> getAllChildren() {
        return ResponseEntity.ok(adminService.getAllChildren());
    }

    @GetMapping("/luggage")
    @Operation(summary = "Get all luggage trackers")
    public ResponseEntity<List<Luggage>> getAllLuggage() {
        return ResponseEntity.ok(adminService.getAllLuggage());
    }

    @PostMapping("/imei")
    @Operation(summary = "Add new IMEI to database")
    public ResponseEntity<Imei> addImei(@RequestBody ImeiDto.ImeiRequest request) {
        return ResponseEntity.ok(adminService.addImei(request.getImei()));
    }

    @GetMapping("/imei")
    @Operation(summary = "Get all IMEI numbers")
    public ResponseEntity<List<Imei>> getAllImeiNumbers() {
        return ResponseEntity.ok(adminService.getAllImeiNumbers());
    }
}