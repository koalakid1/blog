package com.tamlog.blog.api.board.domain;

import com.tamlog.blog.advice.custom.InvalidContentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardContentTest {
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName(value = "본문의 없거나 내용이 빈칸이여서 오류")
    void blank(String blank) {
        assertThrows(InvalidContentException.class, () -> new Content(blank));
    }

    @Test
    @DisplayName(value = "본문의 길이가 길어서 오류")
    void longLength() {
        String longString = "a".repeat(5001);
        assertThrows(InvalidContentException.class, () -> new Content(longString));
    }
}