package com.example.demo.note;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.UUID;

@Service
public class NoteImageService {

    private final NoteImageRepository noteImageRepository;

    public NoteImageService(NoteImageRepository noteImageRepository) {
        this.noteImageRepository = noteImageRepository;
    }

    public String saveImage(String date, MultipartFile file, Long categoryId, Long teamId) throws Exception {

        // 날짜 폴더 생성
        Path folder = Paths.get("uploads/note-images/" + date).toAbsolutePath();
        Files.createDirectories(folder);

        // 확장자 추출
        String original = file.getOriginalFilename();
        String ext = (original != null && original.contains("."))
                ? original.substring(original.lastIndexOf("."))
                : ".png";

        // 새 파일명 생성
        String filename = UUID.randomUUID().toString() + ext;
        Path target = folder.resolve(filename);

        // 파일 저장
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        // URL 구성
        String url = "/note-images/files/" + date + "/" + filename;

        // DB 저장
        NoteImage img = new NoteImage();
        img.setDate(date);
        img.setImgUrl(url);
        img.setCategoryId(categoryId);
        img.setTeamId(teamId);   // ⭐ 추가됨

        noteImageRepository.save(img);

        return url;
    }
}
