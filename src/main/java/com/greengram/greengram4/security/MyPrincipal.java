package com.greengram.greengram4.security;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor //어딘가에서 클래스 객체화 해야됨 - 기본생성자 필요
@AllArgsConstructor //안쓰지만 빌더 때문에 넣음
public class MyPrincipal {//로그인 시 사용자의 pk값 외 다른 값을 받을때 유연하게 사용됨
    private int iuser;


}
