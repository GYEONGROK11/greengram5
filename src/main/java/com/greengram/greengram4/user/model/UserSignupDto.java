package com.greengram.greengram4.user.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "회원가입 데이터")
public class UserSignupDto {
    @JsonIgnore
    private int iuser;
    @Schema(title = "유저ID")
    private String uid;
    @Schema(title = "유저PW")
    private String upw;
    @Schema(title = "유저이름")
    private String nm;
    @Schema(title = "유저사진")
    private String pic;
}
