package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@Tag(name = "Admin Management", description = "Operations related to admin management")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    @Operation(summary = "Get all admins", description = "Retrieve a list of all admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/paged")
    @Operation(summary = "Get all admins (paged)", description = "Retrieve a paginated list of all admins")
    public ResponseEntity<Page<Admin>> getAllAdmins(Pageable pageable) {
        return ResponseEntity.ok(adminService.getAllAdmins(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get admin by ID", description = "Retrieve an admin by their ID")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Get admin by username", description = "Retrieve an admin by their username")
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username) {
        return ResponseEntity.ok(adminService.getAdminByUsername(username));
    }

    @PostMapping
    @Operation(summary = "Create a new admin", description = "Create a new admin account")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        return new ResponseEntity<>(adminService.createAdmin(admin), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update admin", description = "Update an existing admin's information")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.updateAdmin(id, admin));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete admin", description = "Delete an admin by their ID")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}