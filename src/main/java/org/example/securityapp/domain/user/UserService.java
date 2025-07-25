package org.example.securityapp.domain.user;

import lombok.RequiredArgsConstructor;
import org.example.securityapp.controller.dto.UserRequest;
import org.example.securityapp.core.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void 회원가입(UserRequest.Join reqDTO) {
        // 스프링 시큐리티는 db에 저장된 암호화 된 비밀번호를 기준으로 검증한다.
        // 만약 비밀번호가 암호화 되어있지 않으면 처리해주지 않는다. 예외발생
        String encPassword = bCryptPasswordEncoder.encode(reqDTO.getPassword());
        String roles = "USER";
        userRepository.save(reqDTO.getUsername(), encPassword, reqDTO.getEmail(), roles);
    }

    // 스프링 시큐리티가 로그인시 사용하는 UserDetailsService 의 loadUserByUsername 메서드를 재정의해서 사용함
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public String 로그인(UserRequest.Login reqDTO) {
        User user = userRepository.findByUsername(reqDTO.getUsername());

        if (user == null) throw new RuntimeException("유저네임을 찾을 수 없습니다");

        if (!bCryptPasswordEncoder.matches(reqDTO.getPassword(), user.getPassword()))
            throw new RuntimeException("비밀번호가 틀렸습니다");

        // 4. JWT 토큰 생성
        String jwtToken = JwtUtil.create(user);

        return jwtToken;
    }
}