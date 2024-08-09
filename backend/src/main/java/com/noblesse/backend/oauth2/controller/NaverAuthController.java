package com.noblesse.backend.oauth2.controller;

import com.noblesse.backend.oauth2.service.NaverAuthService;
import com.noblesse.backend.oauth2.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth2")
public class NaverAuthController {

    private final NaverAuthService naverAuthService;

    @Autowired
    public NaverAuthController(NaverAuthService naverAuthService) {
        this.naverAuthService = naverAuthService;
    }

    @PostMapping("/login/naver")
    public String loginWithNaver(@RequestParam("code") String code, @RequestParam("state") String state) {
        return naverAuthService.loginWithNaver(code, state);
    }
}
