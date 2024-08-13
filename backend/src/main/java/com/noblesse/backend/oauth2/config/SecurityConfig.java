/*
package com.noblesse.backend.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/oauth2/**").permitAll() // 기본 및 OAuth2 경로 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // 기본 로그인 페이지 설정
                        .defaultSuccessUrl("/home", true) // 로그인 성공 시 8080으로 리다이렉트
                        .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트할 URL
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/") // 로그아웃 후 8080으로 리다이렉트
                        .invalidateHttpSession(true)
                );

        return http.build();
    }
}
*/
