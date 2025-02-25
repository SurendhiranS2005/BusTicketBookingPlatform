package com.example.demo.controller;

import com.example.demo.entity.Ticket;
import com.example.demo.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/getAll")
    public List<Ticket> getAllTicketsWithoutPagination() {
        return ticketService.getAllTicketsWithoutPagination();
    }

    @GetMapping("/get")
    public Page<Ticket> getAllTickets(Pageable pageable) {
        return ticketService.getAllTicketsWithPagination(pageable);
    }

    @PostMapping("/post")
    public List<Ticket> createTickets(@RequestBody List<Ticket> tickets) {
        return ticketService.createTickets(tickets);
    }

    @GetMapping("/get/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PutMapping("/put/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        return ticketService.updateTicket(id, ticket);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
   return "Message Deleted Successfully";    }
}
