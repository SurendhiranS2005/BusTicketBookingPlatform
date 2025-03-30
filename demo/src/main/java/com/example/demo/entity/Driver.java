package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "drivers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long id;
    
    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String address;
    
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @OneToOne(mappedBy = "driver")
    private Bus assignedBus;
}