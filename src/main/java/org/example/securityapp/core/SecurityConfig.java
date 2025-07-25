package org.example.securityapp.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// localhost:8080/user/asidfj
// localhost:8080/join-form
@Configuration
public class SecurityConfig {

    // 서비스에서 사용하기 위해 bean 에 등록함
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // h2 console은 iframe으로 동작하므로, 이 설정이 필요하다
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        // 토큰이 없는 form 요청을 막는 것을 막았다
        http.csrf(configure -> configure.disable());

        // **폼 로그인(form-based login)**을 활성화
        // 사용자가 로그인 폼을 제출하면 Security가 이를 가로채서 인증 처리
        // 내부적으로 UsernamePasswordAuthenticationFilter가 동작
        http.formLogin(form -> form
                .loginPage("/login-form") // 인증이 필요하면 내가 지정한 url 로 리다이렉트 함
                .loginProcessingUrl("/login") // 인증필터가 처리하는 url(변경가능), username=ssar&password=1234 이 타입으로 만 처리함
//                .usernameParameter("email") // username 을 email 로 변경 가능
                .defaultSuccessUrl("/main"));

        // HTTP 요청에 대해 인가(authorization) 설정을 시작
        // 내부적으로 AuthorizationFilter 설정에 해당
        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/main") // 경로 패턴에 대해 인가 규칙을 지정
                        .authenticated() // 위 주소로 요청이 오면 인증 확인. 세션에 authentication 객체 유무 확인
                        .requestMatchers("/user/**")
                        .hasRole("USER")
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .anyRequest() // 위에서 명시하지 않은 나머지 모든 요청을 지정
                        .permitAll() // .anyRequest()로 지정된 모든 경로는 누구나 접근 가능 (인증 필요 없음)
        );

        return http.build();
    }
}