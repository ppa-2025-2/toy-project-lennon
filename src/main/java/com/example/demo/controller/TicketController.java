package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.ChangeTicketStatusDTO;
import com.example.demo.controller.dto.NewTicketDTO;
import com.example.demo.domain.TicketBusiness;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private TicketBusiness ticketBusiness;

    public TicketController(TicketBusiness ticketBusiness) {
        this.ticketBusiness = ticketBusiness;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void newTicket(@RequestBody NewTicketDTO newTicket) {
        ticketBusiness.criarTicket(newTicket);
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void changeTicketStatus(@RequestBody ChangeTicketStatusDTO changeTicketStatusDTO) {
        ticketBusiness.updateTicketStatus(changeTicketStatusDTO);
    }

}
