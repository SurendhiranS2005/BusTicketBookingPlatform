package com.example.demo.service;

import com.example.demo.entity.Bus;
import com.example.demo.entity.Driver;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.repository.BusRepository;
import com.example.demo.repository.DriverRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BusService {
    private final BusRepository busRepository;
    private final DriverRepository driverRepository;

    public BusService(BusRepository busRepository, DriverRepository driverRepository) {
        this.busRepository = busRepository;
        this.driverRepository = driverRepository;
    }

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public Page<Bus> getAllBuses(Pageable pageable) {
        return busRepository.findAll(pageable);
    }

    public Page<Bus> getBusesByStatus(Bus.BusStatus status, Pageable pageable) {
        return busRepository.findByStatus(status, pageable);
    }

    public Bus getBusById(Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + id));
    }

    public Bus getBusByLicensePlate(String licensePlate) {
        return busRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with license plate: " + licensePlate));
    }

    @Transactional
    public Bus createBus(Bus bus) {
        if (busRepository.existsByLicensePlate(bus.getLicensePlate())) {
            throw new ResourceAlreadyExistsException("Bus with license plate " + bus.getLicensePlate() + " already exists");
        }
        return busRepository.save(bus);
    }

    @Transactional
    public Bus updateBus(Long id, Bus busDetails) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + id));

        bus.setMake(busDetails.getMake());
        bus.setModel(busDetails.getModel());
        bus.setCapacity(busDetails.getCapacity());
        bus.setStatus(busDetails.getStatus());

        return busRepository.save(bus);
    }

    @Transactional
    public Bus assignDriver(Long busId, Long driverId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + busId));
        
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + driverId));
        
        bus.setDriver(driver);
        bus.setAssignmentDate(LocalDate.now());
        return busRepository.save(bus);
    }

    @Transactional
    public Bus removeDriver(Long busId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + busId));
        
        bus.setDriver(null);
        bus.setAssignmentDate(null);
        return busRepository.save(bus);
    }

    @Transactional
    public void deleteBus(Long id) {
        if (!busRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bus not found with id: " + id);
        }
        busRepository.deleteById(id);
    }

    public List<Bus> getBusesByDriver(Long driverId) {
        return busRepository.findByDriverId(driverId);
    }
}