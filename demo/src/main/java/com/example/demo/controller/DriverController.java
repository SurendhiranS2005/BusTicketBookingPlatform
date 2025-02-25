package com.example.demo.controller;

import com.example.demo.entity.Driver;
import com.example.demo.service.DriverService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
    @PostMapping("/post")
    public Driver createDriver(@RequestBody Driver driver) {
        return driverService.createDriver(driver);
    }
    @PostMapping("/postmutliple")
    public List<Driver> createMultipleDrivers(@RequestBody List<Driver> drivers) {
        return driverService.createMultipleDrivers(drivers);
    }
    @GetMapping("/get")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }
    @GetMapping("/get/{id}")
    public Driver getDriverById(@PathVariable Long id) {
        return driverService.getDriverById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver not found with ID " + id));
    }
    @PutMapping("/put/{id}")
    public Driver updateDriver(@PathVariable Long id, @RequestBody Driver driver) {
        return driverService.updateDriver(id, driver);
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
    }
}
