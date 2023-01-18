package com.tamlog.blog.domain.user.dto;

import com.tamlog.blog.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String email;
    private String nickname;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}