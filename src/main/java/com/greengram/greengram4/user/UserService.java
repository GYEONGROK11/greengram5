package com.greengram.greengram4.user;

import com.greengram.greengram4.common.Const;
import com.greengram.greengram4.common.ResVo;
import com.greengram.greengram4.user.model.*;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;


    public ResVo signup(UserSignupDto dto){
        String hashcode = BCrypt.hashpw(dto.getUpw(),BCrypt.gensalt());
        UserSignupProcDto pDto = UserSignupProcDto.builder()
                .uid(dto.getUid())
                .upw(hashcode)
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


    public UserSignVo signin(UserSigninDto dto){
        UserSelDto sDto = new UserSelDto();
        sDto.setUid(dto.getUid());
        UserSignVo vo = new UserSignVo();

        UserEntity entity = mapper.selUser(sDto);
        if(entity == null){
            vo.setResult(Const.LOGIN_NO_UID);
        } else if(!BCrypt.checkpw(dto.getUpw(),entity.getUpw())) {
            vo.setResult(Const.LOGIN_DIFF_UPW);
        } else {
            vo.setResult(Const.SUCCESS);
            vo.setIuser(entity.getIuser());
            vo.setNm(entity.getNm());
            vo.setPic(entity.getPic());
        }
        return vo;
    }

    public ResVo follow(UserFollowDto dto){
        int result = mapper.delFollow(dto);

        if(result == 0){
            mapper.insFollow(dto);
            return new ResVo(Const.SUCCESS);
        }
        return new ResVo(Const.FAIL);
    }


    public UserInfoVo userInfo(UserInfoDto dto){
        return mapper.userInfo(dto);

    }
}

