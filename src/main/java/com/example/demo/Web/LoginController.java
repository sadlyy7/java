package com.example.demo.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRepository;

@Controller
public class LoginController {

    private final StudentRepository studentRepository;

    // ⭐ Lombok 대신 직접 생성자 만들기
    public LoginController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/")
    public String home() { return "login"; }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping("/register")
    public String registerPage() { return "login_reg"; }

    @GetMapping("/main")
    public String mainPage() { return "main"; }

    @GetMapping("/brain")
    public String brainPage() { return "brain"; }

    @GetMapping("/note")
    public String notePage() { return "note"; }
    
    @GetMapping("/Memo")
    public String MemoPage() { return "Memo";   }
    

    @PostMapping("/login")
    public String login(String email, String password, HttpSession session) {

        Student student = studentRepository.findByEmail(email);

        if (student == null) {
            return "redirect:/login?error=email";
        }

        if (!student.getPassword().equals(password)) {
            return "redirect:/login?error=password";
        }

        session.setAttribute("student", student);
        return "redirect:/main";
    }
}
