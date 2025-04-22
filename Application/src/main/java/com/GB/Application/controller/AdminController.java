package com.GB.Application.controller;

import com.GB.Application.dto.ImeiDto;
import com.GB.Application.dto.UserDto;
import com.GB.Application.model.*;
import com.GB.Application.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.GB.Application.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin API", description = "Administrative operations")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    // ===== PETS =====
    @GetMapping("/pets")
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(adminService.getAllPets());
    }

    @PostMapping("/pets")
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(adminService.savePet(pet));
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody Pet updatedPet) {
        return ResponseEntity.ok(adminService.updatePet(id, updatedPet));
    }

    @DeleteMapping("/pets/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        adminService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    // ===== CHILDREN =====
    @GetMapping("/children")
    public ResponseEntity<List<Child>> getAllChildren() {
        return ResponseEntity.ok(adminService.getAllChildren());
    }

    @PostMapping("/children")
    public ResponseEntity<Child> createChild(@RequestBody Child child) {
        return ResponseEntity.ok(adminService.saveChild(child));
    }

    @PutMapping("/children/{id}")
    public ResponseEntity<Child> updateChild(@PathVariable Long id, @RequestBody Child updatedChild) {
        return ResponseEntity.ok(adminService.updateChild(id, updatedChild));
    }

    @DeleteMapping("/children/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        adminService.deleteChild(id);
        return ResponseEntity.noContent().build();
    }

    // ===== LUGGAGE =====
    @GetMapping("/luggage")
    public ResponseEntity<List<Luggage>> getAllLuggage() {
        return ResponseEntity.ok(adminService.getAllLuggage());
    }

    @PostMapping("/luggage")
    public ResponseEntity<Luggage> createLuggage(@RequestBody Luggage luggage) {
        return ResponseEntity.ok(adminService.saveLuggage(luggage));
    }

    @PutMapping("/luggage/{id}")
    public ResponseEntity<Luggage> updateLuggage(@PathVariable Long id, @RequestBody Luggage updatedLuggage) {
        return ResponseEntity.ok(adminService.updateLuggage(id, updatedLuggage));
    }

    @DeleteMapping("/luggage/{id}")
    public ResponseEntity<Void> deleteLuggage(@PathVariable Long id) {
        adminService.deleteLuggage(id);
        return ResponseEntity.noContent().build();
    }

    // ===== IMEI =====
    @PostMapping("/imei")
    public ResponseEntity<Imei> addImei(@RequestBody ImeiDto.ImeiRequest request) {
        return ResponseEntity.ok(adminService.addImei(request.getImei()));
    }

    @GetMapping("/imei")
    public ResponseEntity<List<Imei>> getAllImeiNumbers() {
        return ResponseEntity.ok(adminService.getAllImeiNumbers());
    }

    @DeleteMapping("/imei/{id}")
    public ResponseEntity<Void> deleteImei(@PathVariable Long id) {
        adminService.deleteImei(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/imei/{id}")
    public ResponseEntity<Imei> updateImei(@PathVariable Long id, @RequestBody Imei updatedImei) {
        return ResponseEntity.ok(adminService.updateImei(id, updatedImei));
    }
    @GetMapping("/users")
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
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.ok("User '" + username + "' has been deleted.");
    }
}
