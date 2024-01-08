package com.greengram.greengram4.feed;

import com.greengram.greengram4.feed.model.FeedDelDto;
import com.greengram.greengram4.feed.model.FeedInsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedPicsMapper {
    int insFeedPic(FeedInsDto dto);


    List<String> feedSelPics(int ifeed);

    int feedDelPics(FeedDelDto dto);
}
