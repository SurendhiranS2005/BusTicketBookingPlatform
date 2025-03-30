package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @NotBlank(message = "Seat number cannot be blank")
    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Positive(message = "Price must be positive")
    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @NotBlank(message = "Reference number cannot be blank")
    @Column(name = "reference_number", nullable = false, unique = true)
    private String referenceNumber;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum TicketStatus {
        PENDING, CONFIRMED, CANCELLED, USED
    }
}