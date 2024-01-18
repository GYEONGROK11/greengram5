package com.greengram.greengram4.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.greengram.greengram4.common.*;
import com.greengram.greengram4.exception.AuthErrorCode;
import com.greengram.greengram4.exception.RestApiException;
import com.greengram.greengram4.security.AuthenticationFacade;
import com.greengram.greengram4.security.JwtTokenProvider;
import com.greengram.greengram4.security.MyPrincipal;
import com.greengram.greengram4.security.MyUserDetails;
import com.greengram.greengram4.user.model.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;
    private final MyFileUtils myFileUtils;

    public ResVo signup(UserSignupDto dto) {
        //String hashcode = BCrypt.hashpw(dto.getUpw(),BCrypt.gensalt());
        String hashedPw = passwordEncoder.encode(dto.getUpw());
        UserSignupProcDto pDto = UserSignupProcDto.builder()
                .uid(dto.getUid())
                .upw(hashedPw)
                .nm(dto.getNm())
                .pic(dto.getPic())
                .build();
        mapper.insUser(pDto);
        return new ResVo(pDto.getIuser());
    }

    public ResVo patchUserFirebaseToken(UserFirebaseTokenPatchDto dto) {
        int affectedRows = mapper.updUserFirebaseToken(dto);
        return new ResVo(affectedRows);
    }


    public UserSigninVo signin(HttpServletRequest req, HttpServletResponse res, UserSigninDto dto) {
        UserSelDto sDto = new UserSelDto();
        sDto.setUid(dto.getUid());
        UserSigninVo vo = new UserSigninVo();

        UserEntity entity = mapper.selUser(sDto);
        if (entity == null) {
            //vo.setResult(Const.LOGIN_NO_UID);
            throw new RestApiException(AuthErrorCode.NOT_EXIST_USER_ID);
            //} else if(!BCrypt.checkpw(dto.getUpw(),entity.getUpw())) {
        } else if (!passwordEncoder.matches(dto.getUpw(), entity.getUpw())) {
            //vo.setResult(Const.LOGIN_DIFF_UPW);
            throw new RestApiException(AuthErrorCode.VALID_PASSWORD);
        } else {
            vo.setResult(Const.SUCCESS);
            vo.setIuser(entity.getIuser());
            vo.setNm(entity.getNm());
            vo.setPic(entity.getPic());
            vo.setFirebaseToken(entity.getFirebaseToken());

        }
        MyPrincipal myPrincipal = MyPrincipal.builder()
                .iuser(entity.getIuser())
                .build();
        String at = jwtTokenProvider.generateAccessToken(myPrincipal);
        String rt = jwtTokenProvider.generateRefreshToken(myPrincipal);

        //rt를 쿠키에 담음

        int rtCookieMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(res,"rt");
        cookieUtils.setCookie(res,"rt",rt,rtCookieMaxAge);

        HttpSession session = req.getSession(true);
        session.setAttribute("loginUserPk",entity.getIuser());



        vo.setAccessToken(at);
        return vo;
    }

    public ResVo signout(HttpServletResponse res){
        cookieUtils.deleteCookie(res,"rt");
        return new ResVo(1);
    }

    public UserPicPatchDto patchUserPic(MultipartFile pic) {
        UserPicPatchDto dto = new UserPicPatchDto();
        dto.setIuser(authenticationFacade.getLoginUserPk());
        String path = "/user/"+dto.getIuser();
        myFileUtils.delFolderTrigger(path);
        String savedPicFileNm = myFileUtils.transferTo(pic, path);
        dto.setPic(savedPicFileNm);
        int affectedRows = mapper.updUserPic(dto);
        return dto;
    }

    public UserSigninVo getRefreshToken(HttpServletRequest req){//at를 다시 만들어줌
        Cookie cookie = cookieUtils.getCookie(req,"rt");
        UserSigninVo vo = new UserSigninVo();

        if(cookie == null){
            vo.setResult(Const.FAIL);
            vo.setAccessToken(null);
            return vo;
        }

        String token = cookie.getValue();

        if(!jwtTokenProvider.isValidateToken(token)){
            vo.setResult(Const.FAIL);
            vo.setAccessToken(null);
            return vo;
        }

        MyUserDetails myUserDetails = (MyUserDetails) jwtTokenProvider.getUserDetailsFromToken(token);
        MyPrincipal myPrincipal = myUserDetails.getMyPrincipal();

        String at = jwtTokenProvider.generateAccessToken(myPrincipal);

        vo.setResult(Const.SUCCESS);
        vo.setAccessToken(at);
        return vo;
    }

    public ResVo follow(UserFollowDto dto) {
        int result = mapper.delFollow(dto);

        if (result == 0) {
            mapper.insFollow(dto);
            return new ResVo(Const.SUCCESS);
        }
        return new ResVo(Const.FAIL);
    }


    public UserInfoVo userInfo(UserInfoDto dto) {
        return mapper.userInfo(dto);

    }
}

