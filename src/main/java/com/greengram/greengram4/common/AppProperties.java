package com.greengram.greengram4.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app") //pre 접두사 yaml에 app끌어옴
public class AppProperties {

    private final Jwt jwt = new Jwt();//innerclass를 사용하려면 밖의 class에서 객체화를 해야함
    private final Oauth2 oauth2 = new Oauth2();//innerclass를 사용하려면 밖의 class에서 객체화를 해야함

    @Getter
    @Setter
    public static class Jwt { //innerclass로 외부에서 사용을 하려고 하면 사용을 할 수 있지만, 주로 그안의 class에서 사용이 된다
        //이너 클래스는 스태틱으로 만드는게 좋다 -
        private String secret;
        private String headerSchemeName;
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
        private int refreshTokenCookieMaxAge; //쿠키는 초 값으로 넣어야됨

        public void setRefreshTokenExpiry(long refreshTokenExpiry) {
            this.refreshTokenExpiry = refreshTokenExpiry;
            this.refreshTokenCookieMaxAge = (int) refreshTokenExpiry / 1000;
        }

    }

    @Getter
    public static final class Oauth2 {
        private List<String> authorizedRedirectUris = new ArrayList();

    }
}
