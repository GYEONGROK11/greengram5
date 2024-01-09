package com.greengram.greengram4.user;

import com.greengram.greengram4.common.ResVo;
import com.greengram.greengram4.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "유저 API",description = "유저 관련 처리")
public class UserController {
    private final UserService service;

    @PostMapping("/signup")
    @Operation(summary = "회원가입",description = "회원가입 처리")

    public ResVo postSignup(@RequestBody UserSignupDto dto){
        log.info("dto : {}",dto);
        return service.signup(dto);
    }

    @PostMapping("/signin")
    @Operation(summary = "인증",description = "아이디/비번을 활용한 인증처리")
    public UserSigninVo postSignin(@RequestBody UserSigninDto dto){
        return service.signin(dto);
    }

    @PostMapping("/follow")
    public ResVo toggleFollow(@RequestBody UserFollowDto dto){
        return service.follow(dto);
    }

    @GetMapping
    public UserInfoVo userInfo(@RequestBody UserInfoDto dto){
        return service.userInfo(dto);
    }

    @PatchMapping("/firebase-token")
    public ResVo patchUserFirebaseToken(@RequestBody UserFirebaseTokenPatchDto dto) {
        return service.patchUserFirebaseToken(dto);
    }
}
