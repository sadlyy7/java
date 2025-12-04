package com.example.demo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.example.demo.service.TeamRepository;
import com.example.demo.domain.Team;

@Component
public class InitTeamData {

    private final TeamRepository repo;

    public InitTeamData(TeamRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void init() {
        // DB가 비어있을 때만 실행
        if (repo.count() == 0) {
            repo.save(new Team("A팀"));
            repo.save(new Team("B팀"));
            repo.save(new Team("C팀"));
            repo.save(new Team("D팀"));
            repo.save(new Team("E팀"));
        }
    }
}
