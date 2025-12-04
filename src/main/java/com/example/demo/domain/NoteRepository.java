package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    // 기본 기능
    List<Note> findByNoteDate(Date noteDate);
    List<Note> findByStudentId(Long studentId);

    // 🔥 학생 + 필기(NOTE) + 날짜 조회
    List<Note> findByStudentIdAndTypeAndNoteDate(Long studentId, String type, Date date);

    // 🔥 학생 TODO 전체 조회
    List<Note> findByStudentIdAndType(Long studentId, String type);

    // 날짜 목록
    @Query("SELECT DISTINCT n.noteDate FROM Note n ORDER BY n.noteDate DESC")
    List<Date> findDistinctDates();

    // 팀 기능
    List<Note> findByTeamId(Long teamId);
    List<Note> findByTeamIdAndNoteDate(Long teamId, Date noteDate);

    @Query("SELECT DISTINCT n.noteDate FROM Note n WHERE n.teamId = :teamId ORDER BY n.noteDate DESC")
    List<Date> findDistinctDatesByTeam(Long teamId);
}
