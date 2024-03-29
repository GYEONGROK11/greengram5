package com.greengram.greengram4.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {  //enum : Const를 대체해줌
    //400대 에러 : 클라이언트 오류
    //500대 에러 : 서버 오류
    NOT_EXIST_USER_ID(HttpStatus.NOT_FOUND
            , "아이디가 존재하지 않습니다"),
    VALID_PASSWORD(HttpStatus.NOT_FOUND
            , "비밀번호를 확인해 주세요"),
    NEED_SIGNIN(HttpStatus.UNAUTHORIZED
            , "로그인이 필요합니다"),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND
            , "refresh_token이 없습니다");  //enum은 name메소드를 가지고 있고 이것을 리턴함

    private final HttpStatus httpStatus;
    private final String message;
}
