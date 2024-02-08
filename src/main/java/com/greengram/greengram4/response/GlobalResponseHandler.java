package com.greengram.greengram4.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greengram.greengram4.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
//기존 컨트롤러에서 리턴하는 구조(형식)들이 다 다른데 큰 틀을 통일하고 싶어서 사용하였음
//AOP로 해결 ADVICE
//ApiResponse<T>로 통일함
//통일 이유 : 에러와 통신성공 등 구조를 똑같이 만들려고
//@RestControllerAdvice(basePackages = "com.greengram.greengram4") //스웨거에서 처리하는 것을 캐치하지않음
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    private final ObjectMapper om;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //컨트롤러의 반환값이 객체일 때만 return true
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
        //String이 리턴될때는 다른방식을 사용하기 때문에 false로 쳐냈음 - 보통 프론트에서 스트링 리턴됨
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse =
                ((ServletServerHttpResponse) response).getServletResponse();

        int status = servletResponse.getStatus();
        HttpStatus resolve = HttpStatus.resolve(status);
        String path = request.getURI().getPath();
        if(resolve != null) {
            if(resolve.is2xxSuccessful()) { //200대 코드면 통신 성공
                return ApiResponse.builder()
                        .path(path)
                        .data(body)
                        .code("Success")
                        .message("통신 성공")
                        .build();
            } else if(body instanceof ErrorResponse) { //아니면 에러
                Map<String, Object> map = om.convertValue(body, Map.class);
                map.put("path", path);
                return map;
            }
        }
        return body;
    }
}