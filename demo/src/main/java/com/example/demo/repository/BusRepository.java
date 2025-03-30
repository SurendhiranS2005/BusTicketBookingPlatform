package com.example.demo.repository;

import com.example.demo.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, Long> {
    Optional<Bus> findByLicensePlate(String licensePlate);
    boolean existsByLicensePlate(String licensePlate);
    List<Bus> findByDriverId(Long driverId);
    @SuppressWarnings("null")
    Page<Bus> findAll(Pageable pageable);
    Page<Bus> findByStatus(Bus.BusStatus status, Pageable pageable);
}