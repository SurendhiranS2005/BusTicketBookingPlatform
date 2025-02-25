package com.example.demo.service;

import com.example.demo.entity.Bus;
import com.example.demo.repository.BusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService {
    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public Page<Bus> getAllBuses(Pageable pageable) {
        return busRepository.findAll(pageable);
    }

    public Bus getBusById(Long id) {
        return busRepository.findById(id).orElse(null);
    }

    public List<Bus> createBuses(List<Bus> buses) {
        return busRepository.saveAll(buses);
    }

    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }

    public void deleteAllBuses() {
        busRepository.deleteAll();
    }
    public Bus updateBus(Long id, Bus updatedBus) {
        return busRepository.findById(id).map(bus -> {
            bus.setBusNumber(updatedBus.getBusNumber());
            bus.setBusType(updatedBus.getBusType());
            bus.setCapacity(updatedBus.getCapacity());
            return busRepository.save(bus);
        }).orElse(null);
    }
}
