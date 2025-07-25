package org.example.securityapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    // 관리자 권한이 있어야 들어갈 수 있음
    @GetMapping("/admin/main")
    public @ResponseBody String adminMain() {
        return "<h1>admin page</h1>";
    }
}