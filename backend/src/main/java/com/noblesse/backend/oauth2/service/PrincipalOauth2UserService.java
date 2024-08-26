package com.noblesse.backend.oauth2.service;
import com.noblesse.backend.oauth2.dto.NaverUserInfo;
import com.noblesse.backend.oauth2.entity.OAuthUser;
import com.noblesse.backend.oauth2.repository.OAuthRepository;
import com.noblesse.backend.oauth2.security.PrincipalDetails;
import com.noblesse.backend.oauth2.util.JwtUtil; // JwtUtil 경로에 맞게 수정
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private OAuthRepository oAuthRepository;

    @Autowired
    private JwtUtil jwtUtil; // JwtUtil 주입

    @Autowired
    private HttpServletResponse response;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if ("naver".equals(registrationId)) {
            NaverUserInfo naverUserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
            return processUser(naverUserInfo);
        }

        throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token"));
    }

    private PrincipalDetails processUser(NaverUserInfo naverUserInfo) {
        System.out.println("이게뭘까" + naverUserInfo.getName());
        String username = naverUserInfo.getName();
        System.out.println("유저 네임" + username);

        OAuthUser user = oAuthRepository.findByUserName(username)
                .orElseGet(() -> {
                    OAuthUser newUser = new OAuthUser(username, naverUserInfo.getEmail(), naverUserInfo.getProvider(), naverUserInfo.getProviderId());
                    return oAuthRepository.save(newUser);
                });

        // JWT 생성
        String jwtToken = jwtUtil.generateToken(user.getUserId());

        response.setHeader("Authorization", "Bearer " + jwtToken);

        return new PrincipalDetails(user, naverUserInfo.getAttributes());
    }
}