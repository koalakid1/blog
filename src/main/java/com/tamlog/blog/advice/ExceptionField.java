package com.tamlog.blog.advice;

public enum ExceptionField {
    EXCEPTION_ID("id"),
    EXCEPTION_LENGTH("length"),
    EXCEPTION_NAME("name"),
    EXCEPTION_EMAIL("email"),
    EXCEPTION_NICKNAME("nickname"),
    EXCEPTION_EXPIRES_AT("expiresAt"),
    EXCEPTION_REFRESH_TOKEN("refreshToken"),
    EXCEPTION_CATEGORY("category");

    private final String value;

    ExceptionField(String value) {
        this.value = value;
    }

    public static String errorMessageConcat(String message, ExceptionField field, Object value) {
        return String.format(message + " (" + field.value + " : %s", value + ")");
    }
}
