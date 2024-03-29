package com.greengram.greengram4.user.model;

import lombok.Data;

@Data
public class UserModel {
    private int iuser;
    private String uid;
    private String upw;
    private String nm;
    private String pic;
    private String role;
    private String firebaseToken;
    private String createdAt;
    private String updatedAt;
}
