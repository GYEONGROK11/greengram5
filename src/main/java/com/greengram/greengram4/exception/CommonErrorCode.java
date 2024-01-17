package com.greengram.greengram4.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND
            , "리소스를 찾을 수 없습니다"),
    INVALID_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR
            , "서버에러가 발생하였습니다"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST
            , "잘못된 파라미터 입니다");

    private final HttpStatus httpStatus;
    private final String message;
}
