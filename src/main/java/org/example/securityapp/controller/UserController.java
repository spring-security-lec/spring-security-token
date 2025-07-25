package org.example.securityapp.controller;

import org.example.securityapp.domain.user.User;
import org.example.securityapp.domain.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user) {
        System.out.println(user.getUsername());
        return "main";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @PostMapping("/join")
    public String join(String username, String password, String email) {
        userService.회원가입(username, password, email);
        return "redirect:/login-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    // 유저 권한이 있어야 들어갈 수 있음
    @GetMapping("/user")
    public @ResponseBody String user() {
        return "<h1>user page</h1>";
    }
}