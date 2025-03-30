package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "managedBuses")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;
    
    @NotNull
    @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true)
    private String username;
    
    @JsonIgnore
    @NotNull
    @Column(nullable = false)
    private String password;
    
    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @NotNull
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "access_level", nullable = false)
    private AccessLevel accessLevel;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bus> managedBuses;
    
    public enum AccessLevel {
        LOW, MEDIUM, HIGH
    }
}
