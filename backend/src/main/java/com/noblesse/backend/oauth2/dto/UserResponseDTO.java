package com.noblesse.backend.oauth2.dto;

public class UserResponseDTO {
        private String name; // 회원 이름
        private String email; // 이메일 주소
        private String nickname; // 별명
        private String profileImage; // 프로필 사진

        // 생성자, 게터 및 세터 생략


    public UserResponseDTO() {
    }

    public UserResponseDTO(String name, String email, String nickname, String profileImage) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
