package com.greengram.greengram4.user.model;

import lombok.Data;

@Data
public class UserInfoVo {
    private int feedCnt; //등록피드수
    private int favCnt; //등록피드에 받은 좋아요 수
    private String nm;
    private String createdAt;
    private String pic;
    private int follower; // 팔로워수 타겟유저를 팔로우 하는 사람
    private int following; // 팔로잉 수 타겟유저가 팔로우 하는 사람
    private int followState; // 서로 팔로우
    //1 - 로그인 유저가 타겟유저를 팔로우 한 상황
    //2 - 타겟유저가 로그인 유저를 팔로우만 한 상황
    //3 - 서로 팔로우 한 상황
}
