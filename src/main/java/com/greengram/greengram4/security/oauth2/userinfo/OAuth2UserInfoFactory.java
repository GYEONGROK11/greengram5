package com.greengram.greengram4.security.oauth2.userinfo;

import com.greengram.greengram4.security.oauth2.SocialProviderType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OAuth2UserInfoFactory { //스프링 팩토리는 객체화 하는 역할을 전담함
    public OAuth2UserInfo getOAuth2UserInfo(SocialProviderType socialProviderType,//타입에따라 객체화
                                            Map<String,Object> attrs){
        return switch (socialProviderType){
            case KAKAO -> new KakaoOAuth2UserInfo(attrs);
            case NAVER -> new NaverOAuth2UserInfo(attrs);
            default -> throw new IllegalArgumentException("Invalid Provider Type.");
        };
    }
}
