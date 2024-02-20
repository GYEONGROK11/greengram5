package com.greengram.greengram4.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSigninDto {
    @Schema(title = "아이디", example = "rudfhr1")
    private String uid;
    @Schema(title = "비번", example = "1212")
    private String upw;
    @JsonIgnore
    private int iuser;
}
