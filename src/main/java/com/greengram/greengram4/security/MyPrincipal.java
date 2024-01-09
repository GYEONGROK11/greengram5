package com.greengram.greengram4.security;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPrincipal {//로그인 시 사용자의 pk값 외 다른 값을 받을때 유연하게 사용됨
    private int iuser;

}
