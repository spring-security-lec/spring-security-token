package org.example.securityapp.controller;

import org.example.securityapp.core.Resp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class AdminController {

    // 관리자 권한이 있어야 들어갈 수 있음
    @GetMapping
    public Resp<?> adminMain() {
        return new Resp<>();
    }
}