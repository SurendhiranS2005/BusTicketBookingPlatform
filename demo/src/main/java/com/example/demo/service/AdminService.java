package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    public List<Admin> getAllAdminsWithoutPagination() {
        return adminRepository.findAll();
    }
    public Page<Admin> getAllAdminsWithPagination(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public List<Admin> createAdmins(List<Admin> admins) {
        return adminRepository.saveAll(admins);
    }
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        return adminRepository.findById(id).map(admin -> {
            admin.setUsername(updatedAdmin.getUsername());
            admin.setPassword(updatedAdmin.getPassword());
            return adminRepository.save(admin);
        }).orElseThrow(() -> new RuntimeException("Admin not found with ID: " + id));
    }
    


}
