package com.tamlog.blog.api.account.domain;

import com.tamlog.blog.api.account.exception.InvalidNicknameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NicknameTest {
    @DisplayName("유저의 닉네임은 영문, 숫자로 이루어진 15자 이하의 문자열이 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "abcdAbcd!", "abcd12!", "abcd1234^",
            "12345678901234567890113551533", "123456783553539abcdABCD"})
    void invalidNickname(String invalidNickname) {
        assertThrows(InvalidNicknameException.class,
                () -> new Nickname(invalidNickname));
    }

    @DisplayName("제대로 된 nickname")
    @ParameterizedTest
    @ValueSource(strings = {"test", "good", "koalakid", "koalakid154"})
    void validNickname(String validNickname) {
        new Nickname(validNickname);
    }
}