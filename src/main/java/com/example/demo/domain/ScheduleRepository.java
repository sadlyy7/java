package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 🔹 진행 중인 타이머 1개 찾기
    Optional<Schedule> findFirstByStudentIdAndEndTimeIsNull(Long studentId);

    // 🔹 특정 날짜(하루) 기준 조회 – 필요하면 계속 사용 가능
    @Query("SELECT s FROM Schedule s WHERE s.studentId = :studentId AND DATE(s.startTime) = :date")
    List<Schedule> findAllByStudentIdAndDate(
            @Param("studentId") Long studentId,
            @Param("date") LocalDate date
    );

    // 🔥 지난 7일(범위) 조회용
    List<Schedule> findByStudentIdAndStartTimeBetween(
            Long studentId,
            Timestamp start,
            Timestamp end
    );
}
