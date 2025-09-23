package com.example.demo.controller.dto;

import com.example.demo.repository.entity.Ticket;
import jakarta.validation.constraints.NotNull;

public record ChangeTicketStatusDTO(
    @NotNull(message = "O ID do ticket é obrigatório")
    Integer ticketId,
    @NotNull(message = "O novo status é obrigatório")
    Ticket.STATUS newStatus
) {

}