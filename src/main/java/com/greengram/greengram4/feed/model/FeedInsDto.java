package com.greengram.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FeedInsDto {
    @JsonIgnore
    private int ifeed;
    @JsonIgnore
    private int iuser;
    private String contents;
    private String location;
    @JsonIgnore
    private List<MultipartFile> pics;
    //private List<String> pics;
}
