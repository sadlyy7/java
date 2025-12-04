package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrainstormMessageRepository extends JpaRepository<BrainstormMessage, Long> {
    List<BrainstormMessage> findBySpaceId(Long spaceId);
}
