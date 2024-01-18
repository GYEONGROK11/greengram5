package com.greengram.greengram4.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@RestControllerAdvice // advice는 aop용어, aop는 중간에 슬롯처럼 끼워서 사용하는 클래스(보통 로그, 트랜잭션, 예외처리할때 쓰임)
//필터는 튕겨낼때(로그인처리), 인터셉터는 원래는 spring 외부에서 작용 boot버전 부터는 내장이라 필터랑 비슷
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    //서버는 기본적으로 예외로 던지면 예외 처리를 해주지만 우리가 예외번호를 지정해주고 싶을때 만들어서 보냄

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e){
        log.warn("handleIllegalArgument",e);
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER);
    }

    //대부분의 에러처리는 서버에러로 잡음
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e){
        log.warn("handleException",e);
        return handleExceptionInternal(CommonErrorCode.INVALID_SERVER_ERROR);
    }

    //우리가 작업하다가 특정예외를 터트리고 싶어 RestApiException을 만듦
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException e){
        log.warn("handleRestApiException",e);
        return handleExceptionInternal(e.getErrorCode());
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode){
        return handleExceptionInternal(errorCode,null);
    }
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message){
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(message == null
                        ? makeErrorResponse(errorCode)
                        : makeErrorResponse(errorCode, message) );
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode){
        return makeErrorResponse(errorCode, errorCode.getMessage());
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message){
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }
}
