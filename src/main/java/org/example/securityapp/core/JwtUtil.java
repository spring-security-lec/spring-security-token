package org.example.securityapp.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.securityapp.domain.user.User;

import java.util.Date;

// JWT 토큰 생성 및 검증 유틸리티
public class JwtUtil {

    public static final String HEADER = "Authorization"; // HTTP 헤더 이름
    public static final String TOKEN_PREFIX = "Bearer "; // 토큰 접두사
    public static final String SECRET = "메타코딩시크릿키"; // 토큰 서명에 사용될 비밀 키 (강력하게 변경 필요!)
    public static final Long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7; // 7일 (밀리초)

    // JWT 토큰 생성
    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject(user.getUsername()) // 토큰의 주체 (여기서는 사용자 이름)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 토큰 만료 시간
                .withClaim("id", user.getId()) // 사용자 ID 클레임 추가
                .withClaim("roles", user.getRoles()) // 사용자 역할 클레임 추가
                .sign(Algorithm.HMAC512(SECRET)); // 비밀 키로 서명

        return TOKEN_PREFIX + jwt; // "Bearer " 접두사 붙여 반환
    }

    // JWT 토큰 검증 및 디코딩
    public static User verify(String jwt) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(jwt); // 토큰 검증

        Integer id = decodedJWT.getClaim("id").asInt();
        String roles = decodedJWT.getClaim("roles").asString();
        String username = decodedJWT.getSubject();

        return User.builder().id(id).roles(roles).username(username).build();
    }
}