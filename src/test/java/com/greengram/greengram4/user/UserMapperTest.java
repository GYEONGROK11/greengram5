package com.greengram.greengram4.user;

import com.greengram.greengram4.user.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Slf4j
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void insUser() throws Exception {
        UserSignupProcDto dto = UserSignupProcDto.builder()
                .uid("asd")
                .upw("asd")
                .nm("asd")
                .pic("asd")
                .build();

        int result = mapper.insUser(dto);
        assertEquals(1,result);
        assertEquals(true,dto.getIuser()>0);
        log.info("getIuser1111111: {}",dto.getIuser());
        UserSelDto dto1 = new UserSelDto();
        dto1.setUid(dto.getUid());


        UserEntity result1 = mapper.selUser(dto1);
        log.info("result1: {}",result1);

        assertEquals(dto.getIuser(),result1.getIuser());
        assertEquals(dto.getUpw(),result1.getUpw());
        assertEquals(dto.getPic(),result1.getPic());
        assertEquals(dto.getNm(),result1.getNm());

        log.info("{}",dto.getIuser());
    }

    @Test
    void signin() {


    }

    @Test
    void selUser() {
    }

    @Test
    void updUserFirebaseToken() {
    }

    @Test
    void delFollow() {
    }

    @Test
    void insFollow() {
    }

    @Test
    void userInfo() {
    }
}