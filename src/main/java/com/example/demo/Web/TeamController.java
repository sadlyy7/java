package com.example.demo.Web;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.Team;
import com.example.demo.service.TeamService;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "*")
public class TeamController {

    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    // 전체 팀 목록 조회
    @GetMapping
    public List<Team> getTeams() {
        return service.findAll();
    }

    // 팀 생성
    @PostMapping
    public Team createTeam(@RequestParam String name) {
        return service.create(name);
    }

    // 특정 팀 조회
    @GetMapping("/{id}")
    public Team getTeam(@PathVariable Long id) {
        return service.get(id);
    }
}
