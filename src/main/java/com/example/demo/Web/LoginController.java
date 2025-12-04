package com.example.demo.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRepository;

@Controller
public class LoginController {

    private final StudentRepository studentRepository;

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

    // 🔥 note 페이지 들어올 때 세션에서 student 꺼내기
    @GetMapping("/note")
    public String notePage(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/login";
        }
        model.addAttribute("student", student);
        return "note";
    }

    @GetMapping("/Memo")
    public String MemoPage() { return "Memo"; }

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
