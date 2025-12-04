package com.example.demo.Web;

import com.example.demo.domain.Schedule;
import com.example.demo.domain.ScheduleRepository;
import com.example.demo.domain.Student;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class ScheduleController {

    private final ScheduleRepository scheduleRepository;

    // 🔥 대문자 패키지 스캔 문제 회피용 강제 주입 생성자
    @Autowired
    public ScheduleController(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 🔥 타이머 시작
    @PostMapping("/schedule/start")
    public String startTimer(HttpSession session) {

        Student s = (Student) session.getAttribute("student");
        if (s == null) return "NO_USER";

        Optional<Schedule> active =
                scheduleRepository.findFirstByStudentIdAndEndTimeIsNull(s.getId());
        if (active.isPresent()) return "ALREADY_RUNNING";

        Schedule sc = new Schedule();
        sc.setStudentId(s.getId());
        sc.setTitle("공부 타이머");
        sc.setStartTime(Timestamp.valueOf(LocalDateTime.now()));

        scheduleRepository.save(sc);
        return "STARTED";
    }

    // 🔥 타이머 종료
    @PostMapping("/schedule/end")
    public String endTimer(HttpSession session) {

        Student s = (Student) session.getAttribute("student");
        if (s == null) return "NO_USER";

        Optional<Schedule> active =
                scheduleRepository.findFirstByStudentIdAndEndTimeIsNull(s.getId());
        if (active.isEmpty()) return "NO_ACTIVE_TIMER";

        Schedule sc = active.get();
        sc.setEndTime(Timestamp.valueOf(LocalDateTime.now()));

        scheduleRepository.save(sc);
        return "ENDED";
    }

    // 🔥 지난 7일 공부 시간(분 단위)
    @GetMapping("/schedule/week")
    public Map<String, Integer> getWeekStudy(HttpSession session) {

        Student s = (Student) session.getAttribute("student");
        if (s == null) return Map.of();

        LocalDate today = LocalDate.now();
        LocalDate startDay = today.minusDays(6);

        Timestamp start = Timestamp.valueOf(startDay.atStartOfDay());
        Timestamp end = Timestamp.valueOf(today.plusDays(1).atStartOfDay());

        List<Schedule> list = scheduleRepository.findByStudentIdAndStartTimeBetween(
                s.getId(), start, end
        );

        Map<String, Integer> result = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate d = today.minusDays(i);
            result.put(d.toString(), 0);
        }

        for (Schedule sc : list) {
            if (sc.getEndTime() == null || sc.getStartTime() == null) continue;

            long diffMin =
                    (sc.getEndTime().getTime() - sc.getStartTime().getTime()) / 60000;
            if (diffMin < 0) continue;

            LocalDate d = sc.getStartTime().toLocalDateTime().toLocalDate();
            String key = d.toString();

            if (result.containsKey(key)) {
                result.put(key, result.get(key) + (int) diffMin);
            }
        }

        return result;
    }
}
