package org.example.securityapp.domain.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
// Principal -> 인증 객체
// 스프링 시큐리티는 UserDetails 타입의 객체를 받아서 Authentication 객체를 만든다
// 나의 유저 객체를 Authentication 객체에 넣기 위해서 PrincipalDetails 구현하여 포장해서 넣어준다
public class PrincipalDetails implements UserDetails {
    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // 일단 이름은 password 로 고정이다
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // 일단 이름은 username 으로 고정이다
    }
}
