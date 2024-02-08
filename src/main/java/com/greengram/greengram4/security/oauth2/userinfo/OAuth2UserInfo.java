package com.greengram.greengram4.security.oauth2.userinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
//api 구조를 만드는것
@AllArgsConstructor
@Getter
public abstract class OAuth2UserInfo {
    protected Map<String,Object> attributes; //속성

    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();
}
