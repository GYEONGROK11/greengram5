package com.greengram.greengram4.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {
    private final String code;
    private final String message;

    //Errors가 없다면 응답이 내려가지 않게 처리
    //valids가 null이 아니고 size 1 이상이라면 json으로 만들어진다 아니면 안만들어진다
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> valid;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {
        //이너클래스에 static이 없으면 메모리를 비울때 상위 클래스까지 codemessage까지 물고 있어 같이 지워야되서 static을 붙임
        // @Valid 로 에러가 들어왔을 때, 어느 필드에서 에러가 발생했는 지에 대한 응답 처리
        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
