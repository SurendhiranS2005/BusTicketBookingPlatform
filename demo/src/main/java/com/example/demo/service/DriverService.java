package com.example.demo.service;

import com.example.demo.entity.Driver;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DriverRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Page<Driver> getAllDrivers(Pageable pageable) {
        return driverRepository.findAll(pageable);
    }

    public Driver getDriverById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + id));
    }

    public Driver getDriverByLicenseNumber(String licenseNumber) {
        return driverRepository.findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with license number: " + licenseNumber));
    }

    @Transactional
    public Driver createDriver(Driver driver) {
        if (driverRepository.existsByLicenseNumber(driver.getLicenseNumber())) {
            throw new ResourceAlreadyExistsException("Driver with license number " + driver.getLicenseNumber() + " already exists");
        }
        driver.setHireDate(LocalDate.now());
        return driverRepository.save(driver);
    }

    @Transactional
    public Driver updateDriver(Long id, Driver driverDetails) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + id));

        driver.setFullName(driverDetails.getFullName());
        driver.setPhone(driverDetails.getPhone());
        driver.setAddress(driverDetails.getAddress());

        return driverRepository.save(driver);
    }

    @Transactional
    public void deleteDriver(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException("Driver not found with id: " + id);
        }
        driverRepository.deleteById(id);
    }
}