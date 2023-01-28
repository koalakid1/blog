package com.tamlog.blog.api.account.domain;

import com.tamlog.blog.api.account.exception.InvalidPasswordException;
import com.tamlog.blog.api.account.exception.InvalidPasswordFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PasswordTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    Password password;

    String validOne = "abcD1234!";


    @BeforeEach
    void setUp() {
        password = new Password(passwordEncoder.encode(validOne));
    }

    @DisplayName("유저의 비밀번호는 영어 대소문자, 숫자, 특수문자(@$!%*#?&) 세 카테고리를 모두 포함하는 8자이상 20자 이하의 문자열이 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "abcdAbcd", "abCd1234", "12341234", "abcd12!", "abcd1234^",
            "123456789012345678901", "123456789abcdABCD!@#$%"})
    void invalidPasswordFormat(String invalidPassword) {
        assertThrows(InvalidPasswordFormatException.class,
                () -> password.updatePassword(validOne, invalidPassword, passwordEncoder));
    }

    @DisplayName("현재 비밀번호가 매치가 되지 않으면 어떤 예외상황 보다 먼저 알린다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "abcdAbcd", "abCd1234", "12341234", "abcd12!", "abcd1234^",
            "123456789012345678901", "123456789abcdABCD!@#$%"})
    void invalidPassword(String invalidPassword) {
        assertThrows(InvalidPasswordException.class,
                () -> password.updatePassword("1234", invalidPassword, passwordEncoder));
    }

}