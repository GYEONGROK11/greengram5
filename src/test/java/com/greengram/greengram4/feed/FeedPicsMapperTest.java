package com.greengram.greengram4.feed;

import com.greengram.greengram4.feed.model.FeedDelDto;
import com.greengram.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest // 컨테이너에 빈등록된 주소값을 가져올수있게함  - 매퍼는 객체화 됨
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    //h2데이터 베이스를 사용하지 않고 야믈에 세팅한 url기준으로 테스트하겠다는 뜻
    //트랜잭션이 걸려있어 실제로 db에 영향이 가지 않는다
    /*  저장방식
        page, pequest : 요청받을 때 새로 만들어지는 개인 공간
        세션 : 서버(브라우저)가 가지고 있는 개인 공간, 브라우저 닫을 때 까지 유지
        쿠키 : 보안취약 - 사용안함
        JWT :
     */
class FeedPicsMapperTest {

    @Autowired //di해줌  di : bin등록된 주소값 달라고 하는 것
    private FeedPicsMapper picsMapper;

    private FeedInsDto dto;


    public FeedPicsMapperTest(){
        this.dto= new FeedInsDto();
        this.dto.setIfeed(1);

        List<String> pics = new ArrayList<>();
        pics.add("1-1");
        //this.dto.setPics(pics);
        pics.add("1-2");
    }

    @BeforeEach
    public void beforeEach(){
        FeedDelDto dto = new FeedDelDto();
        dto.setIfeed(this.dto.getIfeed());
        dto.setIuser(3);
        int affectedRows = picsMapper.feedDelPics(dto);
    }

    @Test
    void insFeedPic() {
        List<String> preList = picsMapper.feedSelPics(dto.getIfeed());
        assertEquals(0, preList.size());

        //int affectedRows = picsMapper.insFeedPic(dto);

        //assertEquals(dto.getPics().size(), affectedRows);

        List<String> afterList = picsMapper.feedSelPics(dto.getIfeed());

        assertEquals(dto.getPics().size(), afterList.size());

        for (int i = 0; i < dto.getPics().size(); i++) {
            assertEquals(dto.getPics().get(i), afterList.get(i));
        }

    }

}