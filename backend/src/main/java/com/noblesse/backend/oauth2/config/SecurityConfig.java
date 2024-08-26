package com.noblesse.backend.oauth2.config;

import com.noblesse.backend.oauth2.service.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/oauth2/**").permitAll() // 로그인 페이지 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/login") // 커스텀 로그인 페이지 설정
                        .userInfoEndpoint(userInfo -> userInfo.userService(principalOauth2UserService))
                        .failureUrl("/login?error=true")
                                .defaultSuccessUrl("/home", true)// 로그인 실패 시 리다이렉트 URL
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")); // 로그아웃 후 리다이렉트 URL

        return http.build();
    }
}

