package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AdminRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Page<Admin> getAllAdmins(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
    }

    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with username: " + username));
    }

    @Transactional
    public Admin createAdmin(Admin admin) {
        if (adminRepository.existsByUsername(admin.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }
        return adminRepository.save(admin);
    }

    @Transactional
    public Admin updateAdmin(Long id, Admin adminDetails) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));

        admin.setFullName(adminDetails.getFullName());
        admin.setEmail(adminDetails.getEmail());
        admin.setAccessLevel(adminDetails.getAccessLevel());

        return adminRepository.save(admin);
    }

    @Transactional
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new ResourceNotFoundException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }
}