package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.demo.domain.BrainstormSpace;
import com.example.demo.domain.BrainstormSpaceRepository;

@Service
public class BrainstormSpaceService {

    private final BrainstormSpaceRepository repository;

    public BrainstormSpaceService(BrainstormSpaceRepository repository) {
        this.repository = repository;
    }

    // 팀에 공간 없으면 자동 생성
    public Long getOrCreateSpace(Long teamId) {
        return repository.findByTeamId(teamId).stream()
                .findFirst()
                .map(BrainstormSpace::getId)
                .orElseGet(() -> {
                    BrainstormSpace space = new BrainstormSpace(
                            teamId,
                            "팀 " + teamId + " 브레인스토밍",
                            "자동 생성된 공간"
                    );
                    repository.save(space);
                    return space.getId();
                });
    }

    // 공간 생성
    public BrainstormSpace createSpace(Long teamId, String title, String description) {
        BrainstormSpace space = new BrainstormSpace(teamId, title, description);
        return repository.save(space);
    }

    // 특정 팀 공간 전체 조회
    public List<BrainstormSpace> getSpacesByTeam(Long teamId) {
        return repository.findByTeamId(teamId);
    }

    // 상세 조회
    public BrainstormSpace getSpace(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found"));
    }

    // 수정
    public BrainstormSpace updateSpace(Long id, String title, String description) {
        BrainstormSpace space = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found"));

        space.setTitle(title);
        space.setDescription(description);

        return repository.save(space);
    }

    // 삭제
    public void deleteSpace(Long id) {
        repository.deleteById(id);
    }
}
