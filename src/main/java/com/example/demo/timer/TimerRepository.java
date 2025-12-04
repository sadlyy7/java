package com.example.demo.timer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimerRepository extends JpaRepository<TimerEntity, Long> {

    List<TimerEntity> findByUserIdAndDate(Long userId, LocalDate date);

    List<TimerEntity> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
}
