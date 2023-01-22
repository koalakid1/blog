package com.tamlog.blog.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {}

    public static Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security context에 인증정보가 없습니다.");
        }
        System.out.println(authentication.getName());
        return Long.parseLong(authentication.getName());
    }
}
