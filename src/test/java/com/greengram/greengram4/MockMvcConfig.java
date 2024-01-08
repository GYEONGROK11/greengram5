package com.greengram.greengram4;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureMockMvc
@Import({MockMvcConfig.MockMvc.class}) //이너클래스 -클래스 안 클래스 or 에노테이션 안 클래스
public @interface MockMvcConfig { //UTF-8 가능하도록 직접 만들었음
    class MockMvc{
        @Bean
        MockMvcBuilderCustomizer utf8Config(){ //MockMvc가 통신을 할 때 필터를 추가
            return builder ->
                builder.addFilter(new CharacterEncodingFilter("UTF-8",true));
            //UTF-8이 가능하도록 하기위해 세팅
            //람다식 - 콜백함수의 이해가 필요


        }

    }

}
