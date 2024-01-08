package com.greengram.greengram4.feed;

import com.greengram.greengram4.feed.model.FeedDelDto;
import com.greengram.greengram4.feed.model.FeedInsDto;
import com.greengram.greengram4.feed.model.FeedSelDto;
import com.greengram.greengram4.feed.model.FeedSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {

    int insFeed(FeedInsDto dto);

    List<FeedSelVo> feedSel(FeedSelDto dto);

    int feedDel(FeedDelDto dto);

    Integer feedDelUser(FeedDelDto dto);
}
