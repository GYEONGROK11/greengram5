package com.greengram.greengram4.user;

import com.greengram.greengram4.user.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface UserMapper {

    int insUser(UserSignupProcDto dto);

    UserSigninProcVo signin(UserSigninDto dto);

    int updUserPic(UserPicPatchDto dto);

    UserEntity selUser(UserSelDto dto);

    int updUserFirebaseToken(UserFirebaseTokenPatchDto dto);

    int delFollow(UserFollowDto dto);

    int insFollow(UserFollowDto dto);

    UserInfoVo userInfo(UserInfoDto dto);


}
