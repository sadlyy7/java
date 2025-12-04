package com.example.demo.Web;

import com.example.demo.domain.Note;
import com.example.demo.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // 저장
    @PostMapping("/save")
    public String save(@RequestBody Map<String, String> req) {
        Long studentId = Long.parseLong(req.get("studentId"));
        String title = req.get("title");
        String content = req.get("content");
        String type = req.get("type");
        Long teamId = req.get("teamId") != null ? Long.parseLong(req.get("teamId")) : null;
        Date noteDate = Date.valueOf(req.get("noteDate"));

        noteService.save(studentId, title, content, noteDate, type, teamId);
        return "OK";
    }


    // ===============================
    // 🔥 내 TODO 전체 조회
    // ===============================
    @GetMapping("/todo/list")
    public List<Note> getTodoList(@RequestParam Long studentId) {
        return noteService.getTodosByStudent(studentId);
    }

    // ===============================
    // 🔥 내 TODO 날짜별 조회
    // ===============================
    @GetMapping("/todo/list/byDate")
    public List<Note> getTodoListByDate(
            @RequestParam Long studentId,
            @RequestParam String date) {
        return noteService.getTodoByStudentAndDate(studentId, Date.valueOf(date));
    }

    // 기존 기능 유지
    @GetMapping("/dates")
    public List<String> getNoteDates() {
        return noteService.getDistinctNoteDates()
                .stream()
                .map(Date::toString)
                .toList();
    }

    @GetMapping("/list/byStudent")
    public List<Note> getNotesByStudent(@RequestParam Long studentId) {
        return noteService.getNotesByStudent(studentId);
    }

    // 팀 기능
    @GetMapping("/dates/team/{teamId}")
    public List<String> getNoteDatesByTeam(@PathVariable Long teamId) {
        return noteService.getDistinctNoteDatesByTeam(teamId)
                .stream()
                .map(Date::toString)
                .toList();
    }

    @GetMapping("/list/team/{teamId}")
    public List<Note> getNotesByTeamAndDate(
            @PathVariable Long teamId,
            @RequestParam String date) {
        return noteService.getNotesByTeamAndDate(teamId, Date.valueOf(date));
    }
    @GetMapping("/list/byDate")
    public List<Note> getNotesByDate(@RequestParam String date) {
        return noteService.getNotesByDate(Date.valueOf(date));
        
}
    
    @GetMapping("/list/byDate/student")
    public List<Note> getNotesByDateAndStudent(
            @RequestParam Long studentId,
            @RequestParam String date) {
        return noteService.getNotesByStudentAndDate(studentId, Date.valueOf(date));
    }
    }
