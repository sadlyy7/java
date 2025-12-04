package com.example.demo.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteImageRepository extends JpaRepository<NoteImage, Long> {

    // 날짜 목록만 뽑기
    @Query("SELECT DISTINCT n.date FROM NoteImage n ORDER BY n.date DESC")
    List<String> findAllDates();

    // 특정 날짜 이미지들
    List<NoteImage> findByDateOrderByIdDesc(String date);

    // 🔹 카테고리 필터 추가 가능
    List<NoteImage> findByCategoryId(Long categoryId);
    @Query("SELECT DISTINCT n.date FROM NoteImage n WHERE n.teamId = :teamId ORDER BY n.date DESC")
    List<String> findDatesByTeamId(Long teamId);

    // ⭐ 팀 + 날짜별 이미지
    List<NoteImage> findByTeamIdAndDateOrderByIdDesc(Long teamId, String date);

    // ⭐ 팀 전체 이미지 목록 (필기 모아보기 전체 보기)
    List<NoteImage> findByTeamId(Long teamId);
}

