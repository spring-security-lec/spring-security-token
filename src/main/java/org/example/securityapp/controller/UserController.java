package org.example.securityapp.controller;

import org.example.securityapp.domain.user.PrincipalDetails;
import org.example.securityapp.domain.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(principalDetails.getUser().getUsername());
        return "main";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @PostMapping("/join")
    public String join(String username, String password, String email) {
        userService.회원가입(username, password, email);
        return "redirect:/main";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }
}