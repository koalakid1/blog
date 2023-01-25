package com.tamlog.blog.support;

public class ExceptionUtil {
    public static final String EXCEPTION_ID = "id";
    public static final String EXCEPTION_LENGTH = "length";
    public static final String EXCEPTION_NAME = "name";

    public static String errorMessageConcat(String message, String field, Object value) {
        return  String.format(message + " (" + field + " : %s", value + ")");
    }
}
