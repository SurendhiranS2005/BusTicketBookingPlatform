package com.example.demo.controller;

import com.example.demo.entity.Ticket;
import com.example.demo.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Ticket Management", description = "Operations related to ticket management")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    @Operation(summary = "Get all tickets", description = "Retrieve a list of all tickets")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tickets",
            content = @Content(schema = @Schema(implementation = Ticket.class)))
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/paged")
    @Operation(summary = "Get all tickets (paged)", description = "Retrieve a paginated list of all tickets")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated tickets",
            content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<Ticket>> getAllTickets(Pageable pageable) {
        return ResponseEntity.ok(ticketService.getAllTickets(pageable));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get tickets by status", description = "Retrieve tickets by their status (PENDING, CONFIRMED, CANCELLED, USED)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved tickets by status",
            content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<Ticket>> getTicketsByStatus(
            @Parameter(description = "Status of the ticket to filter by", required = true)
            @PathVariable Ticket.TicketStatus status,
            Pageable pageable) {
        return ResponseEntity.ok(ticketService.getTicketsByStatus(status, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by ID", description = "Retrieve a ticket by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ticket",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public ResponseEntity<Ticket> getTicketById(
            @Parameter(description = "ID of the ticket to be retrieved", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/reference/{referenceNumber}")
    @Operation(summary = "Get ticket by reference number", description = "Retrieve a ticket by its reference number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ticket",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public ResponseEntity<Ticket> getTicketByReferenceNumber(
            @Parameter(description = "Reference number of the ticket to be retrieved", required = true)
            @PathVariable String referenceNumber) {
        return ResponseEntity.ok(ticketService.getTicketByReferenceNumber(referenceNumber));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get tickets by user", description = "Retrieve all tickets purchased by a specific user")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user's tickets",
            content = @Content(schema = @Schema(implementation = Ticket.class)))
    public ResponseEntity<List<Ticket>> getTicketsByUser(
            @Parameter(description = "ID of the user whose tickets to retrieve", required = true)
            @PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUser(userId));
    }

    @GetMapping("/bus/{busId}")
    @Operation(summary = "Get tickets by bus", description = "Retrieve all tickets for a specific bus")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved bus tickets",
            content = @Content(schema = @Schema(implementation = Ticket.class)))
    public ResponseEntity<List<Ticket>> getTicketsByBus(
            @Parameter(description = "ID of the bus whose tickets to retrieve", required = true)
            @PathVariable Long busId) {
        return ResponseEntity.ok(ticketService.getTicketsByBus(busId));
    }

    @PostMapping("/user/{userId}/bus/{busId}")
    @Operation(summary = "Create a new ticket", description = "Purchase a new ticket for a specific bus and user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created ticket",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Ticket with reference number already exists")
    })
    public ResponseEntity<Ticket> createTicket(
            @Parameter(description = "Ticket object to be created", required = true)
            @RequestBody Ticket ticket,
            @Parameter(description = "ID of the user purchasing the ticket", required = true)
            @PathVariable Long userId,
            @Parameter(description = "ID of the bus for which the ticket is being purchased", required = true)
            @PathVariable Long busId) {
        return new ResponseEntity<>(
                ticketService.createTicket(ticket, userId, busId),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update ticket", description = "Update an existing ticket's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated ticket",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Ticket> updateTicket(
            @Parameter(description = "ID of the ticket to be updated", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated ticket object", required = true)
            @RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.updateTicket(id, ticket));
    }

    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "Update ticket status", description = "Update the status of a ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated ticket status",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public ResponseEntity<Ticket> updateTicketStatus(
            @Parameter(description = "ID of the ticket to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "New status for the ticket", required = true)
            @PathVariable Ticket.TicketStatus status) {
        return ResponseEntity.ok(ticketService.updateTicketStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete ticket by ID", description = "Cancel/delete a ticket by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted ticket"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public ResponseEntity<Void> deleteTicket(
            @Parameter(description = "ID of the ticket to be deleted", required = true)
            @PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reference/{referenceNumber}")
    @Operation(summary = "Delete ticket by reference number", description = "Cancel/delete a ticket by its reference number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted ticket"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public ResponseEntity<Void> deleteTicketByReferenceNumber(
            @Parameter(description = "Reference number of the ticket to be deleted", required = true)
            @PathVariable String referenceNumber) {
        ticketService.getTicketByReferenceNumber(referenceNumber);
        return ResponseEntity.noContent().build();
    }
}