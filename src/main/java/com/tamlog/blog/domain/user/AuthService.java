package com.tamlog.blog.domain.user;

import com.tamlog.blog.api.dto.UserRequest;
import com.tamlog.blog.config.jwt.TokenProvider;
import com.tamlog.blog.domain.user.dto.TokenResponse;
import com.tamlog.blog.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public UserResponse signup(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        return UserResponse.of(userRepository.save(userRequest.toEntity(passwordEncoder)));
    }

    public TokenResponse login(UserRequest userRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = userRequest.toAuthentication();

        Authentication authenticate = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authenticate);
    }
}
