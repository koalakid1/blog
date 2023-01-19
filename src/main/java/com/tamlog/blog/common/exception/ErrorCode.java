package com.tamlog.blog.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode{
    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_ID(
            HttpStatus.BAD_REQUEST,
        "해당 id에 해당하는 유저가 존재하지 않습니다."
    ),
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "요청 파라미터의 값이 잘못되었습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_ACCESS_TOKEN(
            HttpStatus.UNAUTHORIZED,
        "토큰이 유효하지 않습니다. 토큰을 재발급 받아주세요."
    ),
    INVALID_AUTH_TOKEN(
            HttpStatus.UNAUTHORIZED,
        "토큰에 해당하는 유저가 존재하지 않습니다."
    ),
    //    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */ //
    // USER_NOT_FOUND(HTTP_NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    //    REFRESH_TOKEN_NOT_FOUND(, "로그아웃 된 사용자입니다"),
    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DATA_CONSISTENCY_CONFLICT(HttpStatus.CONFLICT, "데이터 정합성에 모순이 존재합니다.");


    private final HttpStatus httpStatus;
    private final String detail;
}