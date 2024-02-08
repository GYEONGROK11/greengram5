package com.greengram.greengram4.user.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupProcDto {
    private int iuser;
    private String providerType;
    private String uid;
    private String upw;
    private String nm;
    private String role;
    private String pic;
}
