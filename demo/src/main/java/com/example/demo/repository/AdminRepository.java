package com.example.demo.repository;

import com.example.demo.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @SuppressWarnings("null")
    Page<Admin> findAll(Pageable pageable);
}