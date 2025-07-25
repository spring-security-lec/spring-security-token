package org.example.securityapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.securityapp.core.Resp;
import org.example.securityapp.domain.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    // 유저 권한이 있어야 들어갈 수 있음
    @GetMapping
    public Resp<?> user() {
        return new Resp<>();
    }
}