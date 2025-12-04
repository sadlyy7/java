package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.domain.Team;

@Service
public class TeamService {

    private final TeamRepository repo;

    public TeamService(TeamRepository repo) {
        this.repo = repo;
    }

    public List<Team> findAll() {
        return repo.findAll();
    }

    public Team create(String name) {
        return repo.save(new Team(name));
    }

    public Team get(Long id) {
        return repo.findById(id).orElse(null);
    }
}
