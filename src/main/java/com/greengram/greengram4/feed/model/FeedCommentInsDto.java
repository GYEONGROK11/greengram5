package com.greengram.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class FeedCommentInsDto {
    @JsonIgnore
    //@Schema(hidden = true)
    private int ifeedComment;
    @JsonIgnore
    private int iuser;

    @Min(value = 1, message = "ifeed 값은 1 이상입니다") //최소값 1
    private int ifeed;


    @NotEmpty(message = "댓글 내용을 입력해 주세요") //null이거나 비어있으면 안됨 (문자열)
    @Size(min = 2,message = "댓글 내용은 2글자 이상이어야 합니다") //2글자 이상
    private String comment;

}
