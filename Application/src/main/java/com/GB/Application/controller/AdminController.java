package com.GB.Application.controller;

import com.GB.Application.dto.ImeiDto;
import com.GB.Application.dto.UserDto;
import com.GB.Application.model.*;
import com.GB.Application.service.AdminService;
import com.GB.Application.service.TrackerService;
import com.GB.Application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin API", description = "Administrative operations")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final TrackerService trackerService;

    public AdminController(AdminService adminService, UserService userService, TrackerService trackerService) {
        this.adminService = adminService;
        this.userService = userService;
        this.trackerService = trackerService;
    }

    // ===== PETS =====
    @GetMapping("/pets")
    @Operation(summary = "Get all pets", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved pets"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(adminService.getAllPets());
    }

    @PostMapping("/pets")
    @Operation(summary = "Create a new pet", responses = {
            @ApiResponse(responseCode = "200", description = "Pet created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(adminService.savePet(pet));
    }

    @PutMapping("/pets/{id}")
    @Operation(summary = "Update an existing pet", responses = {
            @ApiResponse(responseCode = "200", description = "Pet updated successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody Pet updatedPet) {
        return ResponseEntity.ok(adminService.updatePet(id, updatedPet));
    }

    @DeleteMapping("/pets/{id}")
    @Operation(summary = "Delete a pet by ID", responses = {
            @ApiResponse(responseCode = "204", description = "Pet deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        adminService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    // ===== CHILDREN =====
    @GetMapping("/children")
    @Operation(summary = "Get all children", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved children"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Child>> getAllChildren() {
        return ResponseEntity.ok(adminService.getAllChildren());
    }

    @PostMapping("/children")
    @Operation(summary = "Create a new child", responses = {
            @ApiResponse(responseCode = "200", description = "Child created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Child> createChild(@RequestBody Child child) {
        return ResponseEntity.ok(adminService.saveChild(child));
    }

    @PutMapping("/children/{id}")
    @Operation(summary = "Update an existing child", responses = {
            @ApiResponse(responseCode = "200", description = "Child updated successfully"),
            @ApiResponse(responseCode = "404", description = "Child not found")
    })
    public ResponseEntity<Child> updateChild(@PathVariable Long id, @RequestBody Child updatedChild) {
        return ResponseEntity.ok(adminService.updateChild(id, updatedChild));
    }

    @DeleteMapping("/children/{id}")
    @Operation(summary = "Delete a child by ID", responses = {
            @ApiResponse(responseCode = "204", description = "Child deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Child not found")
    })
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        adminService.deleteChild(id);
        return ResponseEntity.noContent().build();
    }

    // ===== LUGGAGE =====
    @GetMapping("/luggage")
    @Operation(summary = "Get all luggage", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved luggage"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Luggage>> getAllLuggage() {
        return ResponseEntity.ok(adminService.getAllLuggage());
    }

    @PostMapping("/luggage")
    @Operation(summary = "Create a new luggage item", responses = {
            @ApiResponse(responseCode = "200", description = "Luggage created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Luggage> createLuggage(@RequestBody Luggage luggage) {
        return ResponseEntity.ok(adminService.saveLuggage(luggage));
    }

    @PutMapping("/luggage/{id}")
    @Operation(summary = "Update an existing luggage item", responses = {
            @ApiResponse(responseCode = "200", description = "Luggage updated successfully"),
            @ApiResponse(responseCode = "404", description = "Luggage not found")
    })
    public ResponseEntity<Luggage> updateLuggage(@PathVariable Long id, @RequestBody Luggage updatedLuggage) {
        return ResponseEntity.ok(adminService.updateLuggage(id, updatedLuggage));
    }

    @DeleteMapping("/luggage/{id}")
    @Operation(summary = "Delete a luggage item by ID", responses = {
            @ApiResponse(responseCode = "204", description = "Luggage deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Luggage not found")
    })
    public ResponseEntity<Void> deleteLuggage(@PathVariable Long id) {
        adminService.deleteLuggage(id);
        return ResponseEntity.noContent().build();
    }

    // ===== IMEI =====
    @PostMapping("/imei")
    @Operation(summary = "Add a new IMEI", responses = {
            @ApiResponse(responseCode = "200", description = "IMEI added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Imei> addImei(@RequestBody ImeiDto.ImeiRequest request) {
        return ResponseEntity.ok(adminService.addImei(request.getImei()));
    }

    @GetMapping("/imei")
    @Operation(summary = "Get all IMEI numbers", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved IMEI numbers"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Imei>> getAllImeiNumbers() {
        return ResponseEntity.ok(adminService.getAllImeiNumbers());
    }

    @DeleteMapping("/imei/{id}")
    @Operation(summary = "Delete an IMEI by ID", responses = {
            @ApiResponse(responseCode = "204", description = "IMEI deleted successfully"),
            @ApiResponse(responseCode = "404", description = "IMEI not found")
    })
    public ResponseEntity<Void> deleteImei(@PathVariable Long id) {
        adminService.deleteImei(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/imei/{id}")
    @Operation(summary = "Update an existing IMEI", responses = {
            @ApiResponse(responseCode = "200", description = "IMEI updated successfully"),
            @ApiResponse(responseCode = "404", description = "IMEI not found")
    })
    public ResponseEntity<Imei> updateImei(@PathVariable Long id, @RequestBody Imei updatedImei) {
        return ResponseEntity.ok(adminService.updateImei(id, updatedImei));
    }

    // ===== USERS =====
    @GetMapping("/users")
    @Operation(summary = "Get all users", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhoneNumber()
                ))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/users/{username}")
    @Operation(summary = "Delete a user by username", responses = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.ok("User '" + username + "' has been deleted.");
    }

    @GetMapping("/all-trackers")
    @Operation(summary = "Get all trackers (Admin only)", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trackers"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<?>> getAllTrackers() {
        return ResponseEntity.ok(trackerService.getAllTrackers());
    }
}
