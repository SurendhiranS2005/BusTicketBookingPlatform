package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) 
    {
        this.adminService = adminService;
    }

    @GetMapping("/getAll")
    public List<Admin> getAllAdminsWithoutPagination() 
    {
        return adminService.getAllAdminsWithoutPagination();
    }

    @GetMapping("/get")
    public Page<Admin> getAllAdmins(Pageable pageable) 
    {
        return adminService.getAllAdminsWithPagination(pageable);
    }

    @PostMapping("/post")
    public List<Admin> createAdmins(@RequestBody List<Admin> admins)
    {
        return adminService.createAdmins(admins);
    }

    @GetMapping("/get/{id}")
    public Admin getAdminById(@PathVariable Long id) 
    {
        return adminService.getAdminById(id);
    }
    @PutMapping("/put/{id}")
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin admin) 
    {
    return adminService.updateAdmin(id, admin);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id) 
    {
        adminService.deleteAdmin(id);
        return "Deleted Successfully";
    }
}
