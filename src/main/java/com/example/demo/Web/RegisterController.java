package com.example.demo.Web;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final StudentRepository studentRepository;

    // ⭐ Lombok 대신 직접 생성자 추가
    public RegisterController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

   

    @PostMapping("/register")
    public String register(String nickname, String email, String password, HttpSession session) {

        Student s = new Student();
        s.setName(nickname);
        s.setEmail(email);
        s.setPassword(password);
        studentRepository.save(s);
        session.setAttribute("student", s);
        return "redirect:/main";   
    }
}
