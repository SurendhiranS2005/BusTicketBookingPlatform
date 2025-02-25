package com.example.demo.controller;

import com.example.demo.entity.Bus;
import com.example.demo.service.BusService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus")
public class BusController 
{
    private final BusService busService;
    
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("/getAll")
    public Page<Bus> getAllBuses(Pageable pageable) {
        return busService.getAllBuses(pageable);
    }

    @GetMapping("/get/{id}")
    public Bus getBusById(@PathVariable Long id) {
        return busService.getBusById(id);
    }

    @PostMapping("/post")
    public List<Bus> createBuses(@RequestBody List<Bus> buses) {
        return busService.createBuses(buses);
    }

    @PutMapping("/put/{id}")
    public Bus updateBus(@PathVariable Long id, @RequestBody Bus bus) {
        return busService.updateBus(id, bus);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllBuses() {
        busService.deleteAllBuses();
    }
}
