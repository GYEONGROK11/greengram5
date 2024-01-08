package com.greengram.greengram4.feed;

import com.greengram.greengram4.feed.model.FeedDelDto;
import com.greengram.greengram4.feed.model.FeedFavDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedFavMapperTest {

    @Autowired //di해줌  di : bin등록된 주소값 달라고 하는 것
    private FeedFavMapper mapper;

    @Test
    public void insFeedFavTest(){
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(2);
        dto.setIuser(2);

        List<FeedFavDto> result1 = mapper.selFeedFavForTest(dto);
        assertEquals(0,result1.size(),"사이즈가 0인지 확인");

        int affectedRows1 = mapper.insFeedFav(dto);
        assertEquals(1, affectedRows1,"첫번째 인설트");

        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto);
        assertEquals(1,result2.size(),"첫번째 인설트 확인");

        dto.setIfeed(8);
        dto.setIuser(1);

        List<FeedFavDto> result3 = mapper.selFeedFavForTest(dto);
        assertEquals(1,result3.size());

        int affectedRows2 = mapper.insFeedFav(dto);
        assertEquals(1, affectedRows2);

        List<FeedFavDto> result4 = mapper.selFeedFavForTest(dto);
        assertEquals(1,result4.size());
    }

    @Test
    public void delFeedFavTest(){
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(87);
        dto.setIuser(1);

        List<FeedFavDto> result1 = mapper.selFeedFavForTest(dto);
        assertEquals(1,result1.size());

        int affectedRows1 = mapper.delFeedFav(dto);
        assertEquals(1, affectedRows1);

        int affectedRows2 = mapper.delFeedFav(dto);
        assertEquals(0, affectedRows2);


        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto);
        assertEquals(0,result2.size());
    }

    @Test
    public void feedDelFavTest(){
        final int ifeed = 87;
        FeedFavDto dto1 = new FeedFavDto();
        dto1.setIfeed(ifeed);
        List<FeedFavDto> result1 = mapper.selFeedFavForTest(dto1);

        FeedDelDto dto = new FeedDelDto();
        dto.setIfeed(ifeed);
        int affectedRows1 = mapper.feedDelFav(dto);
        assertEquals(result1.size(), affectedRows1);

        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto1);
        assertEquals(0, result2.size());

    }
}