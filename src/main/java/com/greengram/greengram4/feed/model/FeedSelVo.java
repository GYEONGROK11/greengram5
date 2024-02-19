package com.greengram.greengram4.feed.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedSelVo {
    private int ifeed;
    private String contents;
    private String location;
    private String createdAt;
    private List<String> pics;

    private int writerIuser;
    private String writerNm;
    private String writerPic;

    private int isFav;
    private List<FeedCommentSelVo> comments;
    private int isMoreComment;

}
