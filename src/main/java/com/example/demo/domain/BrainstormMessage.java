package com.example.demo.domain;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "brainstorm_messages")
public class BrainstormMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "space_id", nullable = false)
    private Long spaceId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String color;

    @Column(name = "posx", nullable = false)
    private Integer posX = 200;

    @Column(name = "posy", nullable = false)
    private Integer posY = 200;

    @Column(nullable = false)
    private Integer votes = 0;

    @Column(name = "created_at", insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    public Long getId() { return id; }

    public Long getSpaceId() { return spaceId; }
    public void setSpaceId(Long spaceId) { this.spaceId = spaceId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Integer getPosX() { return posX; }
    public void setPosX(Integer posX) { this.posX = posX; }

    public Integer getPosY() { return posY; }
    public void setPosY(Integer posY) { this.posY = posY; }

    public Integer getVotes() { return votes; }
    public void setVotes(Integer votes) { this.votes = votes; }

    public Timestamp getCreatedAt() { return createdAt; }


    public BrainstormMessage() {}
}
