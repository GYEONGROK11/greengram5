package com.greengram.greengram4.dm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.greengram.greengram4.common.Const;
import com.greengram.greengram4.common.ResVo;
import com.greengram.greengram4.dm.model.*;
import com.greengram.greengram4.user.UserMapper;
import com.greengram.greengram4.user.model.UserModel;
import com.greengram.greengram4.user.model.UserSelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DmService {
    private final DmMapper mapper;
    private final UserMapper userMapper;
    private final ObjectMapper objMapper;

    public List<DmSelVo> getDmAll(DmSelDto dto){
        List<DmSelVo> list = mapper.getDmAll(dto);
        return list;
    }

    public DmSelVo postDm(DmInsDto dto){
        Integer isExistDm = mapper.selDmUserCheck(dto);
        if(isExistDm != null){ //이미 방이 있는 경우에 방만들지 않고 리턴 원래 있는 방을 사용
            return null;
        }
        mapper.insDm(dto);
        mapper.insDmUser2(dto);
        UserSelDto usDto = new UserSelDto();
        usDto.setIuser(dto.getOtherPersonIuser());
        UserModel userModel = userMapper.selUser(usDto);

        return DmSelVo.builder()
                .idm(dto.getIdm())
                .otherPersonIuser(userModel.getIuser())
                .otherPersonNm(userModel.getNm())
                .otherPersonPic(userModel.getPic())
                .build();

    }

    public ResVo postDmMsg(DmMsgInsDto dto){
        int insAffectedRows = mapper.insDmMsg(dto);
        if(insAffectedRows ==1){
            int updAffectedRows = mapper.updDmLastMsg(dto);
        }
        LocalDateTime now = LocalDateTime.now(); // 현재 날짜 구하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 포맷 정의
        String createdAt = now.format(formatter); // 포맷 적용

        //상대방의 firebaseToken값 필요. 나의 pic, iuser값 필요.
        UserModel otherPerson = mapper.selOtherPersonByLoginUser(dto);

        try {

            if(otherPerson.getFirebaseToken() != null) {
                DmMsgPushVo pushVo = new DmMsgPushVo();
                pushVo.setIdm(dto.getIdm());
                pushVo.setSeq(dto.getSeq());
                pushVo.setWriterIuser(dto.getLoginedIuser());
                pushVo.setWriterPic(dto.getLoginedPic());
                pushVo.setMsg(dto.getMsg());
                pushVo.setCreatedAt(createdAt);

                //object to json
                String body = objMapper.writeValueAsString(pushVo);
                log.info("body: {}", body);
                Notification noti = Notification.builder()
                        .setTitle("dm")
                        .setBody(body)
                        .build();

                Message message = Message.builder()
                        .setToken(otherPerson.getFirebaseToken())
                        .setNotification(noti)
                        .build();

                //FirebaseMessaging.getInstance().sendAsync(message); 점이 두개오는이유  메소드두개 인데 리턴타입이 같은 클래스임
                FirebaseMessaging fm = FirebaseMessaging.getInstance(); // 얘는 리턴 타입이 같은 클래스
                fm.sendAsync(message);  // 얘는 아님 맞으면  .다시 가능
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResVo(dto.getSeq());

    }


    public List<DmMsgSelVo> getMsAll(DmMsgSelDto dto){
        List<DmMsgSelVo> list = mapper.selDmMsgAll(dto);
        return list;
    }


    public ResVo delDmMsg(DmMsgDelDto dto){
        int result = mapper.delDmMsg(dto);
        if(result == 0){
            return new ResVo(Const.FAIL);
        }

        return new ResVo(Const.SUCCESS);
    }
}
