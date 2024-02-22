package com.greengram.greengram4.feed;

import com.greengram.greengram4.common.ResVo;
import com.greengram.greengram4.exception.FeedErrorCode;
import com.greengram.greengram4.exception.RestApiException;
import com.greengram.greengram4.feed.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
@Tag(name = "피드 API",description = "피드 관련 처리")
public class FeedController {
    private final FeedService service;

    @Operation(summary = "피드 등록",description = "피드 등록 처리")
    @PostMapping
    //public ResVo postFeed(@RequestBody FeedInsDto dto){
    public FeedPicsInsDto postFeed(@RequestPart List<MultipartFile> pics, @RequestPart FeedInsDto dto){

        log.info("{}",dto);
        dto.setPics(pics);
        return service.postFeed(dto);
    }

    @Operation(summary = "피드 리스트",description = "피드 등록 처리")
    @GetMapping
    public List<FeedSelVo> getFeedAll(FeedSelDto dto, @PageableDefault(page = 1, size = 30) Pageable pageable){
        log.info("pageable {}",pageable);
        List<FeedSelVo> vo = service.getFeedAll(dto, pageable);
        log.info("{}",dto);
        return vo;
    }

    @DeleteMapping
    public ResVo delFeed(FeedDelDto dto){
        log.info("{}", dto);
        return service.delFeed(dto);
    }

    @GetMapping("/fav")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 처리: result(1), 좋아요 취소: result(2)")
    })
    public ResVo toggleFeedFav(FeedFavDto dto){
        return service.toggleFeedFav(dto);
    }

    @PostMapping("/comment")
    public ResVo postComment(@Valid @RequestBody FeedCommentInsDto dto){
        return service.postComment(dto);
    }

    @GetMapping("/comment")
    public List<FeedCommentSelVo> getFeedCommentAll(int ifeed){
        return service.getFeedCommentAll(ifeed);
    }



}
