package com.example.demo.repository;

import com.example.demo.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserId(Long userId);
    List<Ticket> findByBusId(Long busId);
    @SuppressWarnings("null")
    Page<Ticket> findAll(Pageable pageable);
    Page<Ticket> findByStatus(Ticket.TicketStatus status, Pageable pageable);
    boolean existsByReferenceNumber(String referenceNumber);
    Optional<Ticket> findByReferenceNumber(String referenceNumber);
}