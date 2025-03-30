package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "buses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Long id;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;
    
    @Column(nullable = false)
    private String make;
    
    @Column(nullable = false)
    private String model;
    
    @Column(nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusStatus status;
    
    @Column(name = "assignment_date")
    private LocalDate assignmentDate;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public enum BusStatus {
        ACTIVE, MAINTENANCE, OUT_OF_SERVICE
    }
}