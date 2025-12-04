package com.example.demo.service;

import com.example.demo.domain.Note;
import com.example.demo.domain.NoteRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // 저장
    public Note save(Long studentId, String title, String content, Date noteDate, String type, Long teamId) {
        Note note = new Note();
        note.setStudentId(studentId);
        note.setTitle(title);
        note.setContent(content);
        note.setNoteDate(noteDate);
        note.setType(type);
        note.setTeamId(teamId);
        return noteRepository.save(note);
    }

    // 🔥 “내 필기(NOTE)” 날짜별 조회
    public List<Note> getNotesByStudentAndDate(Long studentId, Date date) {
        return noteRepository.findByStudentIdAndTypeAndNoteDate(studentId, "NOTE", date);
    }

    // 🔥 TODO 날짜별 조회
    public List<Note> getTodoByStudentAndDate(Long studentId, Date date) {
        return noteRepository.findByStudentIdAndTypeAndNoteDate(studentId, "TODO", date);
    }

    // 기존 기능
    public List<Note> getNotesByDate(Date noteDate) {
        return noteRepository.findByNoteDate(noteDate);
    }

    public List<Note> getNotesByStudent(Long studentId) {
        return noteRepository.findByStudentId(studentId);
    }

    public List<Date> getDistinctNoteDates() {
        return noteRepository.findDistinctDates();
    }

    public List<Note> getTodosByStudent(Long studentId) {
        return noteRepository.findByStudentIdAndType(studentId, "TODO");
    }

    // 팀 기능
    public List<Note> getNotesByTeam(Long teamId) {
        return noteRepository.findByTeamId(teamId);
    }

    public List<Note> getNotesByTeamAndDate(Long teamId, Date noteDate) {
        return noteRepository.findByTeamIdAndNoteDate(teamId, noteDate);
    }

    public List<Date> getDistinctNoteDatesByTeam(Long teamId) {
        return noteRepository.findDistinctDatesByTeam(teamId);
    }
}
