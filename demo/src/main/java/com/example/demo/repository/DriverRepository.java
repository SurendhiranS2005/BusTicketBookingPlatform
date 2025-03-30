package com.example.demo.repository;

import com.example.demo.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByLicenseNumber(String licenseNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    @SuppressWarnings("null")
    Page<Driver> findAll(Pageable pageable);
}