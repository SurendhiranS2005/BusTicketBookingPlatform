package com.example.demo.controller;

import com.example.demo.entity.Driver;
import com.example.demo.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@Tag(name = "Driver Management", description = "Operations related to driver management")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    @Operation(summary = "Get all drivers", description = "Retrieve a list of all drivers")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/paged")
    @Operation(summary = "Get all drivers (paged)", description = "Retrieve a paginated list of all drivers")
    public ResponseEntity<Page<Driver>> getAllDrivers(Pageable pageable) {
        return ResponseEntity.ok(driverService.getAllDrivers(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get driver by ID", description = "Retrieve a driver by their ID")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @GetMapping("/license/{licenseNumber}")
    @Operation(summary = "Get driver by license number", description = "Retrieve a driver by their license number")
    public ResponseEntity<Driver> getDriverByLicenseNumber(@PathVariable String licenseNumber) {
        return ResponseEntity.ok(driverService.getDriverByLicenseNumber(licenseNumber));
    }

    @PostMapping
    @Operation(summary = "Create a new driver", description = "Add a new driver to the system")
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        return new ResponseEntity<>(driverService.createDriver(driver), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update driver", description = "Update an existing driver's information")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id, @RequestBody Driver driver) {
        return ResponseEntity.ok(driverService.updateDriver(id, driver));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete driver", description = "Remove a driver from the system")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}