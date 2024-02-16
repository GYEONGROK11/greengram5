package com.greengram.greengram4.dm;

import com.greengram.greengram4.dm.model.*;
import com.greengram.greengram4.user.model.UserModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper //빈등록 + 다오 매퍼.xml을 객체화
public interface DmMapper {
    //----------------------- t_dm
    int insDm(DmInsDto dto);

    List<DmSelVo> getDmAll(DmSelDto dto);

    UserModel selOtherPersonByLoginUser(DmMsgInsDto dto);

    int updDmLastMsg(DmMsgInsDto dto);

    int updDmLastMsgAfterDelByLastMsg(DmMsgDelDto dto);

    //----------------------- t_dm_user
    int insDmUser(DmInsDto dto);

    int insDmUser2(DmInsDto dto);

    Integer selDmUserCheck(DmInsDto dto);

    //----------------------- t_dm_msg
    int insDmMsg(DmMsgInsDto dto);

    List<DmMsgSelVo> selDmMsgAll(DmMsgSelDto dto);

    int delDmMsg(DmMsgDelDto dto);
}
