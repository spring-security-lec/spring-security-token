package org.example.securityapp.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
        // 1. iframe 허용 -> mysql로 전환하면 삭제
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        // 토큰이 없는 form 요청을 막는 것을 막았다
        // 2. CSRF 비활성화 -> html 사용 안할꺼니까
        http.csrf(csrf -> csrf.disable());

        // 3. 세션 비활성화 (stateless -> 키 전달 안해줌. 응답 종료시 session 공간 비움). 토큰방식 사용할 것임
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

/*
        // **폼 로그인(form-based login)**을 활성화
        // 사용자가 로그인 폼을 제출하면 Security가 이를 가로채서 인증 처리
        // 내부적으로 UsernamePasswordAuthenticationFilter가 동작
        http.formLogin(form -> form
                .loginPage("/login-form") // 인증이 필요하면 내가 지정한 url 로 리다이렉트 함
                .loginProcessingUrl("/login") // 인증필터가 처리하는 url(변경가능), username=ssar&password=1234 이 타입으로 만 처리함
//                .usernameParameter("email") // username 을 email 로 변경 가능
                .defaultSuccessUrl("/main"));
*/
        // 4. 폼 로그인 비활성화 (JWT 사용하므로). (UsernamePasswordAuthenticationFilter 비활성화)
        // /login 로그인 방식 비활성화
        http.formLogin(form -> form.disable());

        // 5. HTTP Basic 인증 비활성화 (BasicAuthenticationFilter 비활성화)
        // 비밀번호를 가지고 인증하는 방식 -> Basic 인증
        // 매 요청마다 id, password를 가지고 가는 방식 -> Basic 인증 방식
        // 가장 안전한 방식
        http.httpBasic(basicLogin -> basicLogin.disable());

        // 6. 커스텀 필터 장착 (인가 필터) - 로그인은 컨트롤러에서 직접처리

        // 7. 예외처리 핸들러 등록 (인증/인가 가 완료되면 어떻게할지(후처리), 예외가 발생하면 어떻게 할지). 예외처리 핸들러 수정

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