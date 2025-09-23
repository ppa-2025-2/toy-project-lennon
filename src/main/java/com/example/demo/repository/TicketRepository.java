package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;

import com.example.demo.repository.entity.Ticket;

public interface TicketRepository extends ListCrudRepository<Ticket, Integer>  {
    @NonNull Optional<Ticket> findById(@NonNull Integer id);
    @NonNull Ticket save(@NonNull Ticket ticket);
    void changeTicketStatus(Ticket ticket, Ticket.STATUS status);
}
