package com.greengram.greengram4.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyPrincipal {//로그인 시 사용자의 pk값 외 다른 값을 받을때 유연하게 사용됨
    private int iuser;

}
