package com.greengram.greengram4.feed;

import com.greengram.greengram4.entity.FeedEntity;
import com.greengram.greengram4.entity.FeedPicsEntity;
import com.greengram.greengram4.entity.UserEntity;
import com.greengram.greengram4.feed.model.FeedSelDto;
import com.greengram.greengram4.feed.model.FeedSelVo;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface FeedQdslRepository {
    List<FeedEntity> selFeedAll(FeedSelDto dto, Pageable pageable);

    List<FeedPicsEntity> selFeedPicsAll(List<FeedEntity> feedEntityList);
}
