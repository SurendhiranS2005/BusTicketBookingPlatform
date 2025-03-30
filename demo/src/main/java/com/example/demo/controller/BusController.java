package com.example.demo.controller;

import com.example.demo.entity.Bus;
import com.example.demo.service.BusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
@Tag(name = "Bus Management", description = "Operations related to bus management")
public class BusController {
    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    @Operation(summary = "Get all buses", description = "Retrieve a list of all buses")
    public ResponseEntity<List<Bus>> getAllBuses() {
        return ResponseEntity.ok(busService.getAllBuses());
    }

    @GetMapping("/paged")
    @Operation(summary = "Get all buses (paged)", description = "Retrieve a paginated list of all buses")
    public ResponseEntity<Page<Bus>> getAllBuses(Pageable pageable) {
        return ResponseEntity.ok(busService.getAllBuses(pageable));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get buses by status", description = "Retrieve buses by their status (ACTIVE, MAINTENANCE, OUT_OF_SERVICE)")
    public ResponseEntity<Page<Bus>> getBusesByStatus(
            @PathVariable Bus.BusStatus status,
            Pageable pageable) {
        return ResponseEntity.ok(busService.getBusesByStatus(status, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bus by ID", description = "Retrieve a bus by its ID")
    public ResponseEntity<Bus> getBusById(@PathVariable Long id) {
        return ResponseEntity.ok(busService.getBusById(id));
    }

    @GetMapping("/license/{licensePlate}")
    @Operation(summary = "Get bus by license plate", description = "Retrieve a bus by its license plate")
    public ResponseEntity<Bus> getBusByLicensePlate(@PathVariable String licensePlate) {
        return ResponseEntity.ok(busService.getBusByLicensePlate(licensePlate));
    }

    @GetMapping("/driver/{driverId}")
    @Operation(summary = "Get buses by driver", description = "Retrieve all buses assigned to a specific driver")
    public ResponseEntity<List<Bus>> getBusesByDriver(@PathVariable Long driverId) {
        return ResponseEntity.ok(busService.getBusesByDriver(driverId));
    }

    @PostMapping
    @Operation(summary = "Create a new bus", description = "Add a new bus to the system")
    public ResponseEntity<Bus> createBus(@RequestBody Bus bus) {
        return new ResponseEntity<>(busService.createBus(bus), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update bus", description = "Update an existing bus's information")
    public ResponseEntity<Bus> updateBus(@PathVariable Long id, @RequestBody Bus bus) {
        return ResponseEntity.ok(busService.updateBus(id, bus));
    }

    @PostMapping("/{busId}/assign-driver/{driverId}")
    @Operation(summary = "Assign driver to bus", description = "Assign a driver to a specific bus")
    public ResponseEntity<Bus> assignDriver(@PathVariable Long busId, @PathVariable Long driverId) {
        return ResponseEntity.ok(busService.assignDriver(busId, driverId));
    }

    @PostMapping("/{busId}/remove-driver")
    @Operation(summary = "Remove driver from bus", description = "Remove the currently assigned driver from a bus")
    public ResponseEntity<Bus> removeDriver(@PathVariable Long busId) {
        return ResponseEntity.ok(busService.removeDriver(busId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete bus", description = "Remove a bus from the system")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.noContent().build();
    }
}