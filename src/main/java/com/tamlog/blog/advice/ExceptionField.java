package com.tamlog.blog.advice;

public enum ExceptionField {
    EXCEPTION_ID("id"),
    EXCEPTION_LENGTH("length"),
    EXCEPTION_NAME("name");

    private final String value;

    ExceptionField(String value) {
        this.value = value;
    }

    public static String errorMessageConcat(String message, ExceptionField field, Object value) {
        return String.format(message + " (" + field.value + " : %s", value + ")");
    }
}
