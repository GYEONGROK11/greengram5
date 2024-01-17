package com.greengram.greengram4.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {  //enum : Const를 대체해줌

    VALID_PASSWORD(HttpStatus.BAD_REQUEST
            , "비밀번호를 확인해 주세요"),
    NEED_SIGNIN(HttpStatus.UNAUTHORIZED
            , "로그인이 필요합니다"),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND
            , "refresh_token이 없습니다");  //enum은 name메소드를 가지고 있고 이것을 리턴함

    private final HttpStatus httpStatus;
    private final String message;
}
