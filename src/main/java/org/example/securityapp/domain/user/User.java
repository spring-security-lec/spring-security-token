package org.example.securityapp.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user_tb")
public class User implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String username;
    private String password;
    private String email;
    // 권한이 2가지 이상이라면 "역할1, 역할2" 처럼 저장해야함(문자열로)
    private String roles; // USER, ADMIN

    @Builder
    public User(Integer id, String username, String password, String email, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        String[] roleList = this.roles.split(",");

        for (String role : roleList) {
            authorities.add(() -> "ROLE_" + role); // ROLE_ 접두사 필수
        }

        return authorities;
    }
}