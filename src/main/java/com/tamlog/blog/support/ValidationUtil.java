package com.tamlog.blog.support;

public class ValidationUtil {

    /**
     * @NotBlank() BLANK_멤버변수
     */
    public static final String BLANK_TITLE = "제목이 비었습니다.";
    public static final String BLANK_CONTENT = "본문이 비었습니다.";

    /**
     * @Positive()
     */
    public static final String NOT_POSITIVE = "양수만 입력하세요.";

    /**
     * @Pattern()
     */
    public static final String NOT_PATTERN_PASSWORD = "비밀번호가 양식에 맞지 않습니다.";
}
