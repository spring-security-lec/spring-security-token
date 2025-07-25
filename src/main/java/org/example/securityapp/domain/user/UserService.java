package org.example.securityapp.domain.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void 회원가입(String username, String password, String email) {
        // 스프링 시큐리티는 db에 저장된 암호화 된 비밀번호를 기준으로 검증한다.
        // 만약 비밀번호가 암호화 되어있지 않으면 처리해주지 않는다. 예외발생
        String encPassword = bCryptPasswordEncoder.encode(password);
        userRepository.save(username, encPassword, email);
    }

    // 스프링 시큐리티가 로그인시 사용하는 UserDetailsService 의 loadUserByUsername 메서드를 재정의해서 사용함
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}