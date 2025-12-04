package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Team() {}

    public Team(String name) {
        this.name = name;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }
}
