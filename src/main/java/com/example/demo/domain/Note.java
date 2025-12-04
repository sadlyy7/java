package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "note_date")
    private java.sql.Date noteDate;

    @Column(length = 10, nullable = false)
    private String type;   // NOTE / TODO

    @Column(name = "created_at", insertable = false, updatable = false)
    private java.sql.Timestamp createdAt;

    // ⭐ 추가되는 부분
    @Column(name = "team_id")
    private Long teamId;

    // ===== Getter / Setter =====

    public Long getId() { return id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public java.sql.Date getNoteDate() { return noteDate; }
    public void setNoteDate(java.sql.Date noteDate) { this.noteDate = noteDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public java.sql.Timestamp getCreatedAt() { return createdAt; }

    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }
}
