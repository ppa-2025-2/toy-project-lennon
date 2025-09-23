package com.example.demo.repository.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "tickets")
public class Ticket {

    public enum STATUS {
        CRIADO,
        ANDAMENTO,
        CONCLUIDO,
        CANCELADO
    }

    public Ticket(User creator, User assignee, STATUS status, Set<User> observers, String object, String action, String details, String locality) {
        this.creator = creator;
        this.assignee = assignee;
        this.status = status;
        this.observers = observers;
        this.object = object;
        this.action = action;
        this.details = details;
        this.locality = locality;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 255)
    private User creator;

    @Column(nullable = false, unique = true, length = 255)
    private User assignee;

    @OneToMany
    @JoinTable(name = "tickets_observers", joinColumns = @JoinColumn(name = "ticket_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> observers;

    @Column(nullable = false, length = 255)
    private String object;

    @Column(nullable = false, length = 255)
    private String action;

    @Column(nullable = false, length = 255)
    private String details;

    @Column(nullable = false, length = 255)
    private String locality;

    @Column(nullable = false, length = 255)
    private STATUS status;

    @Column(nullable = false, length = 255 )
    private Date created_at;

    @Column(nullable = true, length = 255)
    private Date updated_at;

}
