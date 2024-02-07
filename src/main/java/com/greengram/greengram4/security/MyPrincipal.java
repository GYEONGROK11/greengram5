package com.greengram.greengram4.security;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor //어딘가에서 클래스 객체화 해야됨 - 기본생성자 필요
@AllArgsConstructor //안쓰지만 빌더 때문에 넣음
public class MyPrincipal {//로그인 시 사용자의 pk값 외 다른 값을 받을때 유연하게 사용됨
    private int iuser;

    @Builder.Default //빌더 패턴을 쓸때 빌더에서 안적으면 디폴트를 이것으로 쓰겠다. 지금은 뉴 어레이 리스트
    private List<String> roles = new ArrayList<>();


}
