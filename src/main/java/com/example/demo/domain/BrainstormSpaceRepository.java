package com.example.demo.domain;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrainstormSpaceRepository extends JpaRepository<BrainstormSpace, Long> {
    List<BrainstormSpace> findByTeamId(Long teamId);
}
