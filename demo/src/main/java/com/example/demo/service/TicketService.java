package com.example.demo.service;

import com.example.demo.entity.Ticket;
import com.example.demo.repository.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTicketsWithoutPagination() {
        return ticketRepository.findAll();
    }

    public Page<Ticket> getAllTicketsWithPagination(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public List<Ticket> createTickets(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getSeatNumber() == null || ticket.getSeatNumber().isEmpty()) {
                throw new RuntimeException("Seat number is required for all tickets.");
            }
        }
        return ticketRepository.saveAll(tickets);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    public Ticket updateTicket(Long id, Ticket updatedTicket) {
        return ticketRepository.findById(id).map(ticket -> {
            ticket.setTitle(updatedTicket.getTitle());
            ticket.setDescription(updatedTicket.getDescription());
            if (updatedTicket.getSeatNumber() != null && !updatedTicket.getSeatNumber().isEmpty()) {
                ticket.setSeatNumber(updatedTicket.getSeatNumber());
            }
            return ticketRepository.save(ticket);
        }).orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + id));
    }
}
