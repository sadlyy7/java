package com.example.demo.timer;

public class TimerRequest {

    private Long userId;
    private String duration;

    public TimerRequest() {}

    public Long getUserId() {
        return userId;
    }

    public String getDuration() {
        return duration;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
