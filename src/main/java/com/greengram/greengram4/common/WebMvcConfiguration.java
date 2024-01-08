package com.greengram.greengram4.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Slf4j
@Configuration // 스프링에 설정파일로 등록되며 (WebMvcConfiguration이)빈등록이 되어 설정할때 거기있는 필요할때 메소드를 실행해줌
public class WebMvcConfiguration implements WebMvcConfigurer {
    //주소값 엔터 시 or 새로고침 시 화이트라벨 오류가 뜸
    //요청시 dispatcherServlet에서 어디에 배치를 할지 핸들러 매핑에 물어본후 컨트롤러에 알려줌
    //매핑되는 곳이 없으면 스태틱 화면을 찾음 - 각 주소값 폴더의 index.html 을 응답함
    //
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/**")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        // If we actually hit a file, serve that. This is stuff like .js and .css files.
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }
                        // Anything else returns the index.
                        return new ClassPathResource("/static/index.html");
                    }
                });
    }
}