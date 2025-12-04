package com.example.demo.note;

import jakarta.persistence.*;

@Entity
@Table(name = "note_image")
public class NoteImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이미지 파일명 또는 URL
    @Column(nullable = false)
    private String imgUrl;

    // yyyy-MM-dd 형태 날짜
    @Column(nullable = false)
    private String date;

    // 카테고리 ID (선택)
    private Long categoryId;

    // ⭐ 팀 ID
    @Column(nullable = true)
    private Long teamId;

    public NoteImage() {}

    public NoteImage(Long id, String imgUrl, String date, Long categoryId, Long teamId) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.date = date;
        this.categoryId = categoryId;
        this.teamId = teamId;
    }

    // Getter / Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }
}
