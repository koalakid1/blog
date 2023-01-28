package com.tamlog.blog.api.account.domain;

import com.tamlog.blog.api.account.exception.InvalidEmailException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {"email@example", "email@example.", "email@.com", "plainaddress", "@noplace.com"})
    void invalidEmail(String invalidEmail) {
        assertThrows(InvalidEmailException.class,
                () -> new Email(invalidEmail));
    }

    @ParameterizedTest
    @ValueSource(strings = {"email@example.com", "firstname.lastname@example.com", "email@sub.example.com"})
    void validEmail(String validEmail) {
        Email email = new Email(validEmail);
    }

}