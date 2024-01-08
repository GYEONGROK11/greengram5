package com.greengram.greengram4;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
//@Import(CharEncodingConfig.class) -한글 안깨지게 만듦
@MockMvcConfig //한글 안깨지게 만듦
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //서버 랜덤한 포트에서 함 8080대신
@AutoConfigureMockMvc
@Transactional //트랜잭션 : 커밋할지 롤백할지 테스트는 롤백
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //DB를 자바에서 제공하는거 말고 우리 DB로 테스트함
public class BaseIntegrationTest { //통합테스트 공통 사용할 수 있는유저 피드 디엠 다 다르기 때문에
    // 에노테이션 상속받아 쓰려고 하나 만들었음

    @Autowired protected MockMvc mvc;
    //빈등록 : 스프링한테 클래스의 객체화 담당, 객체화된다면 주소값을 가지고 있고, 필요 시 주소값을 받을 수 있는 것

    @Autowired //di해줌  di : bin등록된 주소값 달라고 하는 것
    //Inversion of Control(ioc)-코드작성방식 반대순서로작성함
    //DI : 의존 관계 주입(Dependency Injection)이라고도 하며,
    //어떤 객체가 사용하는 의존 객체를 직접 만들어 사용하는게 아니라, 주입 받아 사용하는 방법이다.
    //(new 연산자를 이용해서 객체를 생성하는 것이라고 생각하면 된다)
    //@Autowired : 필요한 의존 객체의 “타입"에 해당하는 빈을 찾아 주입한다.
    // Bean 등록 후 매개변수에 주소값을 주입, 생성자를 통해 주입받는 것을 추천, 매개변수에 0개 안됨, 1개 무조건 넣어야 함.
    protected ObjectMapper om; //객체를 json형태로 바꿔줄때 사용, 반대도 사용




}
