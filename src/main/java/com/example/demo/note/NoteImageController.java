package com.example.demo.note;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/note-images")
public class NoteImageController {

    private final NoteImageService noteImageService;
    private final NoteImageRepository noteImageRepository;

    public NoteImageController(NoteImageService noteImageService,
                               NoteImageRepository noteImageRepository) {
        this.noteImageService = noteImageService;
        this.noteImageRepository = noteImageRepository;
    }

    // ================================
    // 🔹 전체 날짜 목록
    // ================================
    @GetMapping("/dates")
    public List<String> getAllDates() {
        return noteImageRepository.findAllDates();
    }

    // ================================
    // 🔹 특정 날짜 이미지 목록
    // ================================
    @GetMapping
    public List<String> getImagesByDate(@RequestParam String date) {
        return noteImageRepository.findByDateOrderByIdDesc(date)
                .stream()
                .map(NoteImage::getImgUrl)
                .collect(Collectors.toList());
    }

    // ================================
    // 🔥 팀별 날짜 목록
    // ================================
    @GetMapping("/dates/team/{teamId}")
    public List<String> getDatesByTeam(@PathVariable Long teamId) {
        return noteImageRepository.findDatesByTeamId(teamId);
    }

    // ================================
    // 🔥 팀별 + 날짜별 이미지 목록
    // ================================
    @GetMapping("/team/{teamId}")
    public List<String> getImagesByTeamAndDate(
            @PathVariable Long teamId,
            @RequestParam String date) {

        return noteImageRepository
                .findByTeamIdAndDateOrderByIdDesc(teamId, date)
                .stream()
                .map(NoteImage::getImgUrl)
                .collect(Collectors.toList());
    }

    // ================================
    // 🔥 특정 팀의 전체 이미지 (필기 모아보기)
    // ================================
    @GetMapping("/team/{teamId}/all")
    public List<String> getAllImagesByTeam(@PathVariable Long teamId) {
        return noteImageRepository.findByTeamId(teamId)
                .stream()
                .map(NoteImage::getImgUrl)
                .collect(Collectors.toList());
    }

    // ================================
    // 🔥 이미지 업로드 + 저장
    // ================================
    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("date") String date,
            @RequestParam(value = "teamId", required = false) Long teamId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "categoryId", required = false) Long categoryId
    ) {
        try {
            String url = noteImageService.saveImage(date, file, categoryId, teamId);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("error");
        }
    }
}
