package org.example.securityapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.securityapp.controller.dto.UserRequest;
import org.example.securityapp.core.Resp;
import org.example.securityapp.domain.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;

    /**
     * username=ssar&password=1234&email=ssar@nate.com
     * <p>
     * { "username":"ssar", "password":1234, "email":"ssar@nate.com"}
     */
    @PostMapping("/join")
    public Resp<?> join(@RequestBody UserRequest.Join reqDTO) {
        userService.회원가입(reqDTO);
        return new Resp<>();
    }

    @PostMapping("/login")
    public Resp<?> login(@RequestBody UserRequest.Login reqDTO) {
        String accessToken = userService.로그인(reqDTO);
        return new Resp<>(accessToken);
    }
}