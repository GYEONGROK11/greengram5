package com.greengram.greengram4.feed;

import com.greengram.greengram4.common.*;
import com.greengram.greengram4.entity.FeedEntity;
import com.greengram.greengram4.entity.FeedPicsEntity;
import com.greengram.greengram4.entity.UserEntity;
import com.greengram.greengram4.exception.FeedErrorCode;
import com.greengram.greengram4.exception.RestApiException;
import com.greengram.greengram4.feed.model.*;
import com.greengram.greengram4.security.AuthenticationFacade;
import com.greengram.greengram4.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;
    private final FeedRepository repository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade; //로그인과 관련
    private final MyFileUtils myFileUtils;

    @Transactional
    public FeedPicsInsDto postFeed(FeedInsDto dto) {
        if(dto.getPics() == null){
            throw new RestApiException(FeedErrorCode.PICS_MORE_THEN_ONE);
        }
        UserEntity userEntity= userRepository.getReferenceById((long)authenticationFacade.getLoginUserPk());

        FeedEntity feedEntity = new FeedEntity();
        feedEntity.setUserEntity(userEntity);
        feedEntity.setContents(dto.getContents());
        feedEntity.setLocation(dto.getLocation());
        repository.save(feedEntity);

        String target = "/feed/"+ feedEntity.getIfeed();
        FeedPicsInsDto picsDto = new FeedPicsInsDto();
        picsDto.setIfeed(feedEntity.getIfeed().intValue());

        for (MultipartFile file: dto.getPics()) {
            String saveFileNm = myFileUtils.transferTo(file, target);
            picsDto.getPics().add(saveFileNm);

        }
        List<FeedPicsEntity>feedPicsEntityList = picsDto.getPics()
                .stream()
                .map(item -> FeedPicsEntity.builder()
                        .feedEntity(feedEntity)
                        .pic(item)
                        .build()
                ).collect(Collectors.toList());
        feedEntity.getFeedPicsEntityList().addAll(feedPicsEntityList);


        return picsDto;
    }

    /*@Transactional //(모든작업이 한작업이됨 지금은 매퍼가 2개있는데 둘다 성공해야 커밋됨)
    public FeedPicsInsDto postFeed(FeedInsDto dto) {
        if(dto.getPics() == null){
            throw new RestApiException(FeedErrorCode.PICS_MORE_THEN_ONE);
        }
        dto.setIuser(authenticationFacade.getLoginUserPk());
        log.info("{}",dto);
        int feedAffectedRows = mapper.insFeed(dto);
        String target = "/feed/"+dto.getIfeed();
        FeedPicsInsDto picsDto = new FeedPicsInsDto();
        picsDto.setIfeed(dto.getIfeed());
        for (MultipartFile file: dto.getPics()) {
            String saveFileNm = myFileUtils.transferTo(file, target);
            picsDto.getPics().add(saveFileNm);
        }
        picsMapper.insFeedPic(picsDto);
        log.info("{}",feedAffectedRows);
        return picsDto;
    }*/

    public List<FeedSelVo> getfeedAll(FeedSelDto dto, Pageable pageable) {
        List<FeedSelVo> list = null;
        if(dto.getIsFavList() == 0 && dto.getTargetIuser() > 0){
            repository.findAllByUserEntityOrderByIfeedDesc(null, pageable);
        }

            list = mapper.feedSel(dto);

        FeedCommentSelDto fcDto = new FeedCommentSelDto();
        fcDto.setStartIdx(0);
        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CMT);
        for (FeedSelVo vo : list) {
            List<String> pics = picsMapper.feedSelPics(vo.getIfeed());
            vo.setPics(pics);

            fcDto.setIfeed(vo.getIfeed());
            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(fcDto);

            vo.setComments(comments);

            if (comments.size() == 4) {
                vo.setIsMoreComment(1);
                comments.remove(comments.size() - 1);
            }
        }
        return list;
    }

    /*public List<FeedSelVo> getfeedAll(FeedSelDto dto) {
        List<FeedSelVo> list = mapper.feedSel(dto);

        FeedCommentSelDto fcDto = new FeedCommentSelDto();
        fcDto.setStartIdx(0);
        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CMT);
        for (FeedSelVo vo : list) {
            List<String> pics = picsMapper.feedSelPics(vo.getIfeed());
            vo.setPics(pics);

            fcDto.setIfeed(vo.getIfeed());
            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(fcDto);

            vo.setComments(comments);

            if (comments.size() == 4) {
                vo.setIsMoreComment(1);
                comments.remove(comments.size() - 1);
            }
        }
        return list;
    }*/

    public List<FeedCommentSelVo> getFeedCommentAll(int ifeed) {
        FeedCommentSelDto dto = new FeedCommentSelDto();
        dto.setIfeed(ifeed);
        dto.setStartIdx(3);
        dto.setRowCount(999);

        return commentMapper.selFeedCommentAll(dto);

    }

    public ResVo delFeed(FeedDelDto dto) {
        Integer ifeed = mapper.feedDelUser(dto);

        if (ifeed == null) {
            return new ResVo(Const.FAIL);
        }
        int picsAffectedRow = picsMapper.feedDelPics(dto);
        if(picsAffectedRow == 0){
            return new ResVo(Const.FAIL);
        }
        favMapper.feedDelFav(dto);
        commentMapper.feedDelComment(dto);
        mapper.feedDel(dto);
        return new ResVo(Const.SUCCESS);
    }

    public ResVo toggleFeedFav(FeedFavDto dto) {
        int result = favMapper.delFeedFav(dto);
        if (result == 0) {
            favMapper.insFeedFav(dto);
            return new ResVo(Const.FEED_FAV_ADD);
        }
        return new ResVo(Const.FEED_FAV_DEL);
    }

    public ResVo postComment(FeedCommentInsDto dto) {
        //ifeed == 0 or comment == null or comment ==""
//        if(dto.getIfeed() == 0 || dto.getComment().isBlank()){
//            throw new RestApiException(FeedErrorCode.IMPOSSIBLE_REG_COMMENT);
//        }
        dto.setIuser(authenticationFacade.getLoginUserPk());
        int affectedRow = commentMapper.insFeedComment(dto);
        return new ResVo(dto.getIfeedComment());
    }
}
