package com.example.demo.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.validation.annotation.Validated;

import com.example.demo.controller.dto.ChangeTicketStatusDTO;
import com.example.demo.controller.dto.NewTicketDTO;
import com.example.demo.domain.stereotype.Business;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Ticket;
import com.example.demo.repository.entity.User;

import jakarta.validation.Valid;

@Business
@Validated
public class TicketBusiness {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketBusiness(
            TicketRepository ticketRepository,
            UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public void criarTicket(@Valid NewTicketDTO newTicket) {
        User creator = this.userRepository.findByEmail(newTicket.creatorEmail())
            .orElseThrow(() -> new IllegalArgumentException("Criador não encontrado"));

        User assignee = this.userRepository.findByEmail(newTicket.assigneeEmail())
            .orElseThrow(() -> new IllegalArgumentException("Criador não encontrado"));

        Set<User> observers = new HashSet<>();
        observers.addAll(this.userRepository.findByEmailIn(newTicket.observerEmails()));

        Ticket ticket = new Ticket(creator, assignee, Ticket.STATUS.CRIADO, observers, newTicket.object(),
            newTicket.action(), newTicket.details(), newTicket.locality());

        ticketRepository.save(ticket);
    }

    public void updateTicketStatus(@Valid ChangeTicketStatusDTO changeTicketStatusDTO) {
        Ticket ticket = this.ticketRepository.findById(changeTicketStatusDTO.ticketId())
            .orElseThrow(() -> new IllegalArgumentException("Ticket"));
        
        this.ticketRepository.changeTicketStatus(ticket, changeTicketStatusDTO.newStatus());
    }
}
