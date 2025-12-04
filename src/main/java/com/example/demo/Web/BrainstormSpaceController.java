package com.example.demo.Web;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.BrainstormSpace;
import com.example.demo.service.BrainstormSpaceService;

@RestController
@RequestMapping("/api/brainstorm/spaces")
@CrossOrigin(origins = "*")
public class BrainstormSpaceController {

    private final BrainstormSpaceService service;

    public BrainstormSpaceController(BrainstormSpaceService service) {
        this.service = service;
    }

    // 팀 ID 기반 자동 생성 + Space ID 반환
    @GetMapping("/spaceId/{teamId}")
    public Long getOrCreateSpace(@PathVariable Long teamId) {
        return service.getOrCreateSpace(teamId);
    }

    // 공간 생성
    @PostMapping
    public BrainstormSpace createSpace(
            @RequestParam Long teamId,
            @RequestParam String title,
            @RequestParam String description) {
        return service.createSpace(teamId, title, description);
    }

    // 특정 팀의 공간 목록 조회
    @GetMapping("/team/{teamId}")
    public List<BrainstormSpace> getSpaces(@PathVariable Long teamId) {
        return service.getSpacesByTeam(teamId);
    }

    // 공간 상세 조회
    @GetMapping("/{id}")
    public BrainstormSpace getSpace(@PathVariable Long id) {
        return service.getSpace(id);
    }

    // 공간 수정
    @PutMapping("/{id}")
    public BrainstormSpace updateSpace(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam(required = false) String description) {

        return service.updateSpace(id, title, description);
    }

    // 공간 삭제
    @DeleteMapping("/{id}")
    public void deleteSpace(@PathVariable Long id) {
        service.deleteSpace(id);
    }
}
