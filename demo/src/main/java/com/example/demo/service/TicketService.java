package com.example.demo.service;

import com.example.demo.entity.Ticket;
import com.example.demo.entity.Bus;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.BusRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final BusRepository busRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, 
                        BusRepository busRepository,
                        UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.busRepository = busRepository;
        this.userRepository = userRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Page<Ticket> getAllTickets(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    public Page<Ticket> getTicketsByStatus(Ticket.TicketStatus status, Pageable pageable) {
        return ticketRepository.findByStatus(status, pageable);
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
    }

    public Ticket getTicketByReferenceNumber(String referenceNumber) {
        return ticketRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with reference number: " + referenceNumber));
    }

    @Transactional
    public Ticket createTicket(Ticket ticket, Long userId, Long busId) {
        if (ticketRepository.existsByReferenceNumber(ticket.getReferenceNumber())) {
            throw new ResourceAlreadyExistsException("Ticket with reference number " + ticket.getReferenceNumber() + " already exists");
        }
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + busId));
        
        ticket.setUser(user);
        ticket.setBus(bus);
        ticket.setPurchaseDate(LocalDateTime.now());
        
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket updateTicket(Long id, Ticket ticketDetails) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));

        ticket.setSeatNumber(ticketDetails.getSeatNumber());
        ticket.setPrice(ticketDetails.getPrice());
        ticket.setStatus(ticketDetails.getStatus());

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket updateTicketStatus(Long id, Ticket.TicketStatus status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        
        ticket.setStatus(status);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found with id: " + id);
        }
        ticketRepository.deleteById(id);
    }

  

    public List<Ticket> getTicketsByUser(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    public List<Ticket> getTicketsByBus(Long busId) {
        return ticketRepository.findByBusId(busId);
    }
}