package com.example.demo.timer;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TimerService {

    private final TimerRepository repo;

    public TimerService(TimerRepository repo) {
        this.repo = repo;
    }

    private int toSec(String t) {
        String[] arr = t.split(":");
        return Integer.parseInt(arr[0]) * 3600 +
               Integer.parseInt(arr[1]) * 60 +
               Integer.parseInt(arr[2]);
    }

    private String format(int sec) {
        int h = sec / 3600;
        sec %= 3600;
        int m = sec / 60;
        sec %= 60;
        return String.format("%02d:%02d:%02d", h, m, sec);
    }

    public Map<String, Object> save(TimerRequest req) {

        TimerEntity entity = new TimerEntity(req.getUserId(), req.getDuration());
        repo.save(entity);

        LocalDate today = LocalDate.now();
        List<TimerEntity> todayList = repo.findByUserIdAndDate(req.getUserId(), today);

        int total = todayList.stream()
                .mapToInt(t -> toSec(t.getDuration()))
                .sum();

        Map<String, Object> res = new HashMap<>();
        res.put("totalDuration", format(total));

        return res;
    }

    public List<Integer> getWeek(Long userId, LocalDate start) {

        LocalDate end = start.plusDays(6);

        List<TimerEntity> list = repo.findByUserIdAndDateBetween(userId, start, end);

        int[] week = new int[7];

        for (TimerEntity t : list) {
            int index = t.getDate().getDayOfWeek().getValue() - 1; // 월(1)~일(7) -> 0~6
            week[index] += toSec(t.getDuration());
        }

        List<Integer> result = new ArrayList<>();
        for (int sec : week) result.add(sec);

        return result;
    }
}
