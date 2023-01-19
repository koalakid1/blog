package com.tamlog.blog.domain.user;

import com.tamlog.blog.api.dto.UserChangePasswordRequest;
import com.tamlog.blog.common.exception.CustomException;
import com.tamlog.blog.common.exception.ErrorCode;
import com.tamlog.blog.common.util.SecurityUtil;
import com.tamlog.blog.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse findCurrentUser() {
        return UserResponse.of(findBySecurity());
    }

    @Transactional
    public UserResponse updateUserNickname(String nickname) {
        User user = findBySecurity();

        user.updateNickname(nickname);

        return UserResponse.of(user);
    }

    @Transactional
    public UserResponse updateUserPassword(UserChangePasswordRequest request) {
        User user = findBySecurity();

        if (!passwordEncoder.matches(user.getPassword(), request.getExPassword())){
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));

        return UserResponse.of(user);
    }

    private User findBySecurity() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID));
    }
}
