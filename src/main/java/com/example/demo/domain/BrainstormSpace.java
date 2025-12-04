package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "brainstorm_spaces")
public class BrainstormSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teamId;
    private String title;
    private String description;

    public BrainstormSpace() {}

    public BrainstormSpace(Long teamId, String title, String description) {
        this.teamId = teamId;
        this.title = title;
        this.description = description;
    }

    public Long getId() { return id; }
    public Long getTeamId() { return teamId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public void setTeamId(Long teamId) { this.teamId = teamId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
}
