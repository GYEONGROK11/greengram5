package com.greengram.greengram4.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public MyUserDetails getLoginUser(){

        return (MyUserDetails)SecurityContextHolder
                .getContext()
                .getAuthentication() //토큰값이 넘어옴
                .getPrincipal(); //오브젝트에서 형변환
    }

    public int getLoginUserPk(){
        MyUserDetails myUserDetails = getLoginUser();
        return myUserDetails == null
                ? 0
                : myUserDetails
                .getMyPrincipal()
                .getIuser();

    }
}
