package com.example.demo.timer;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class TimerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String duration;

    private LocalDate date;

    public TimerEntity() {} // JPA 기본 생성자

    public TimerEntity(Long userId, String duration) {
        this.userId = userId;
        this.duration = duration;
        this.date = LocalDate.now();
    }

    // ===== Getter/Setter =====

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getDuration() {
        return duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
