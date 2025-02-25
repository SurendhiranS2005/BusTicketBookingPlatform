package com.example.demo.service;

import com.example.demo.entity.Driver;
import com.example.demo.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public List<Driver> createMultipleDrivers(List<Driver> drivers) {
        return driverRepository.saveAll(drivers);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> getDriverById(Long id) {
        return driverRepository.findById(id);
    }

    public Driver updateDriver(Long id, Driver updatedDriver) {
        return driverRepository.findById(id).map(driver -> {
            driver.setName(updatedDriver.getName());
            driver.setLicenseNumber(updatedDriver.getLicenseNumber());
            return driverRepository.save(driver);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver not found with ID: " + id));
    }

    @Transactional
    public void deleteDriver(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver not found with ID: " + id);
        }
        driverRepository.deleteById(id);
    }
}
