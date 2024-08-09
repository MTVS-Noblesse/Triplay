package com.noblesse.backend.oauth2.service;

import com.noblesse.backend.oauth2.dto.UserResponseDTO;
import com.noblesse.backend.oauth2.util.JwtUtil;
import com.noblesse.backend.user.entity.User;
import com.noblesse.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class NaverAuthService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @Autowired
    public NaverAuthService(RestTemplate restTemplate, UserRepository userRepository, JwtUtil jwtUtil) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String loginWithNaver(String code, String state) {
        String accessToken = getAccessToken(code, state);
        UserResponseDTO userResponse = getUserInfo(accessToken);
        User user = saveUser(userResponse);
        return jwtUtil.generateToken(user.getUserEmail());
    }

    private String getAccessToken(String code, String state) {
        String url = UriComponentsBuilder.fromHttpUrl("https://nid.naver.com/oauth2.0/token")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .queryParam("state", state)
                .queryParam("grant_type", "authorization_code")
                .toUriString();

        Map<String, String> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("access_token")) {
            return response.get("access_token");
        } else {
            throw new RuntimeException("Failed to retrieve access token.");
        }
    }

    private UserResponseDTO getUserInfo(String accessToken) {
        String url = "https://openapi.naver.com/v1/nid/me";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        Map<String, Object> response = restTemplate.getForObject(url, Map.class, headers);

        if (response != null && response.containsKey("response")) {
            Map<String, Object> userInfo = (Map<String, Object>) response.get("response");

            UserResponseDTO userResponse = new UserResponseDTO();
            userResponse.setName((String) userInfo.get("name"));
            userResponse.setEmail((String) userInfo.get("email"));
            userResponse.setNickname((String) userInfo.get("nickname"));
            userResponse.setProfileImage((String) userInfo.get("profile_image"));

            return userResponse;
        } else {
            throw new RuntimeException("Failed to retrieve user information.");
        }
    }

    private User saveUser(UserResponseDTO userResponse) {
        User user = userRepository.findByUserEmail(userResponse.getEmail());
        if (user == null) {
            user = new User();
            user.setUserName(userResponse.getName());
            user.setUserEmail(userResponse.getEmail());
            user.setUserNickname(userResponse.getNickname());
            user.setProfileUrl(userResponse.getProfileImage());
            userRepository.save(user);
        }
        return user;
    }
}
