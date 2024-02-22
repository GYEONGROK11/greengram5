package com.greengram.greengram4.feed;

import com.greengram.greengram4.entity.FeedEntity;
import com.greengram.greengram4.entity.UserEntity;
import com.greengram.greengram4.feed.model.FeedSelDto;
import com.greengram.greengram4.feed.model.FeedSelVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;

import static com.greengram.greengram4.entity.QFeedEntity.feedEntity;
import static com.greengram.greengram4.entity.QUserEntity.userEntity;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class FeedQdslRepositoryImpl implements FeedQdslRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FeedEntity> selFeedAll(FeedSelDto dto, Pageable pageable) {
        List<FeedEntity> feedList = jpaQueryFactory.select(feedEntity)
                .from(feedEntity)
                .join(feedEntity.userEntity).fetchJoin() //피드하나당 유저정보(글쓴이)는 한명이라 페치조인으로 정보 다 들고오기

                .where(whereTargetUser(dto.getTargetIuser())) //(whereTargetUser(targetIuser),whereTargetUser(targetIuser)) 쉼표로 and조건 사용가능
                .orderBy(feedEntity.ifeed.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return feedList;


        /*return feedList.stream().map(item ->
                FeedSelVo.builder()
                        .ifeed(item.getIfeed().intValue())
                        .contents(item.getContents())
                        .location(item.getContents())
                        .createdAt(item.getCreatedAt().toString())
                        .writerIuser(item.getUserEntity().getIuser().intValue())
                        .writerNm(item.getUserEntity().getNm())
                        .writerPic(item.getUserEntity().getPic())
                        .pics(item.getFeedPicsEntityList().stream().map(pic ->
                                pic.getPic()).collect(Collectors.toList()))
                        .isFav(item.getFeedFavList().stream().anyMatch(fav ->
                                fav.getUserEntity().getIuser() == dto.getLoginedIuser()
                        )? 1 : 0)

                        .build()).collect(Collectors.toList());*/
    }

    private BooleanExpression whereTargetUser(long targetIuser){
        return targetIuser ==0 ? null : feedEntity.userEntity.iuser.eq(targetIuser);
    }
}
