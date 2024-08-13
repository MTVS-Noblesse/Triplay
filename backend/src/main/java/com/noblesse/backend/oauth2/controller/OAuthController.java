package com.noblesse.backend.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@Controller
public class OAuthController {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private RestTemplate restTemplate; // RestTemplate을 Bean으로 주입받기

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login/oauth2/code/naver")
    public String naverCallback(@RequestParam String code, @RequestParam String state) {
        System.out.println("오긴 오노?");

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("naver", "user");
        OAuth2AccessToken accessToken = null;
        // authorizedClient가 null인 경우 처리
        if (authorizedClient != null) {
            accessToken = authorizedClient.getAccessToken();
        } else {
            System.out.println("클라이언트가 널");
            // 에러 처리 로직 추가 (예: 로그인 페이지로 리다이렉트)
            return "redirect:/login?error=invalid_client";
        }

        // accessToken이 null인 경우 처리
        if (accessToken == null) {
            System.out.println("액세스 토큰이 널");
            return "redirect:/login?error=access_denied";
        }

        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken.getTokenValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, String.class);
            String userInfo = response.getBody();
            System.out.println("userInfo = " + userInfo);
        } catch (Exception e) {
            System.out.println("호출이 실패");
            e.printStackTrace();
            return "redirect:/login?error=api_error"; // API 호출 실패시 처리
        }
        System.out.println("여기까지 오긴 하노");
        return "redirect:login"; // 성공적으로 처리 후 홈으로 리다이렉트
    }
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
