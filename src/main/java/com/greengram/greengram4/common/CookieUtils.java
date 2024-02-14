package com.greengram.greengram4.common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Base64;
import java.util.Optional;

@Component //빈등록 - 객체화를 한것 new cookieutils를 스프링 컨테이너가 해주고 주소값을 가지고 있음
public class CookieUtils {
    public Optional<Cookie> getCookie(HttpServletRequest request, String name) { //쿠키 get
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/"); //이 주소값 기준으로 쿠키가 만들어짐
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie); //브라우저 당 생성이 되고 쿠키가 올 때 마다 담겨서 오게 된다
    }

    public void deleteCookie(HttpServletResponse response, String name) {//로그아웃 시 쿠키삭제
        Cookie cookie = new Cookie(name,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public String serialize(Object obj){
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj));
    }

    public <T> T deserialize(Cookie cookie, Class<T> cls){
        return cls.cast(
                SerializationUtils.deserialize(
                        //Base64.getDecoder().decode(cookie.getValue())
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }
}
