package com.example.demo.timer;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/timer")
public class TimerController {

    private final TimerService service;

    // 🔥 롬복 대신 직접 생성자 만들기
    public TimerController(TimerService service) {
        this.service = service;
    }

    /** 🔥 타이머 저장 */
    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody TimerRequest req) {
        return service.save(req);
    }

    /** 🔥 주간 데이터 조회 */
    @GetMapping("/week")
    public List<Integer> week(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(defaultValue = "1") Long userId
    ) {
        return service.getWeek(userId, start);
    }
}
