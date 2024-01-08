package com.greengram.greengram4.feed;

import com.greengram.greengram4.common.ResVo;
import com.greengram.greengram4.feed.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class) //스프링 컨테이너를 사용할 수 있도록
@Import({FeedService.class})  //특정한 ()의객체를 임폴트함 : 빈등록되있는거 객체화함
class FeedServiceTest {
    @MockBean private FeedMapper mapper; //가짜 빈을 만들어 스프링 컨테이너가 주소값을 넣어줌
    @MockBean private FeedPicsMapper picsMapper; //아무것도 값을 넣지않고 리턴하면 리턴해주는 타입의 디폴드값을 리턴함
    @MockBean private FeedFavMapper favMapper;
    @MockBean private FeedCommentMapper commentMapper;//사용하지않아도 모두 주입해야 service 호출가능
    @Autowired private FeedService service;

    @Test
    void postFeed() {
        when(mapper.insFeed(any())).thenReturn(2);
        when(picsMapper.insFeedPic(any())).thenReturn(3);

        FeedInsDto dto = new FeedInsDto();
        dto.setIfeed(100);
        ResVo vo = service.postFeed(dto);
        assertEquals(dto.getIfeed(),vo.getResult());

        verify(mapper).insFeed(any()); //정말 메소드 호출했는지 체크
        verify(picsMapper).insFeedPic(any());

        FeedInsDto dto2 = new FeedInsDto();
        dto2.setIfeed(200);
        ResVo vo2 = service.postFeed(dto2);
        assertEquals(dto2.getIfeed(),vo2.getResult());


    }

    @Test
    void feedSel() {
        FeedSelVo feedSelVo1 = new FeedSelVo();
        feedSelVo1.setIfeed(1);
        feedSelVo1.setContents("1번");

        FeedSelVo feedSelVo2 = new FeedSelVo();
        feedSelVo2.setIfeed(2);
        feedSelVo2.setContents("2번");

        List<FeedSelVo> list = new ArrayList<>();
        list.add(feedSelVo1);
        list.add(feedSelVo2);

        when(mapper.feedSel(any())).thenReturn(list);

        List<String> feed1Pics = Arrays.stream(new String[]{"1-1","1-2"}).toList();

        List<String> feed2Pics =new ArrayList<>();
        feed2Pics.add("2-1");
        feed2Pics.add("2-2");

        List<List<String>> picsList = new ArrayList<>();
        picsList.add(feed1Pics);
        picsList.add(feed2Pics);

        List<String>[] picsArr = new List[2];
        picsArr[0] = feed1Pics;
        picsArr[1] = feed2Pics;

        when(picsMapper.feedSelPics(1)).thenReturn(feed1Pics);
        when(picsMapper.feedSelPics(2)).thenReturn(feed2Pics);



        List<FeedCommentSelVo> feed1comment =new ArrayList<>();
        FeedCommentSelVo selvo1 = new FeedCommentSelVo();
        selvo1.setIfeedComment(1);
        selvo1.setComment("댓글1-1");
        feed1comment.add(selvo1);
        FeedCommentSelVo selvo2 = new FeedCommentSelVo();
        selvo2.setIfeedComment(2);
        selvo2.setComment("댓글1-2");
        feed1comment.add(selvo2);
        FeedCommentSelVo selvo3 = new FeedCommentSelVo();
        selvo3.setIfeedComment(3);
        selvo3.setComment("댓글1-3");
        feed1comment.add(selvo1);
        FeedCommentSelVo selvo4 = new FeedCommentSelVo();
        selvo4.setIfeedComment(4);
        selvo4.setComment("댓글1-4");
        feed1comment.add(selvo1);

        List<FeedCommentSelVo> feed2comment =new ArrayList<>();
        FeedCommentSelVo selvo5 = new FeedCommentSelVo();
        selvo5.setIfeedComment(5);
        selvo5.setComment("댓글2-1");
        feed2comment.add(selvo5);
        FeedCommentSelVo selvo6 = new FeedCommentSelVo();
        selvo6.setIfeedComment(6);
        selvo6.setComment("댓글2-2");
        feed2comment.add(selvo6);
        FeedCommentSelVo selvo7 = new FeedCommentSelVo();
        selvo7.setIfeedComment(7);
        selvo7.setComment("댓글2-3");
        feed2comment.add(selvo7);
        FeedCommentSelVo selvo8 = new FeedCommentSelVo();
        selvo8.setIfeedComment(8);
        selvo8.setComment("댓글2-4");
        feed2comment.add(selvo8);


        List<List<FeedCommentSelVo>> commentList = new ArrayList<>();
        commentList.add(feed1comment);
        commentList.add(feed2comment);

        FeedCommentSelDto dto1 = new FeedCommentSelDto();
        dto1.setIfeed(feedSelVo1.getIfeed());
        dto1.setStartIdx(0);
        dto1.setRowCount(4);
        FeedCommentSelDto dto2 = new FeedCommentSelDto();
        dto2.setIfeed(feedSelVo2.getIfeed());
        dto2.setStartIdx(0);
        dto2.setRowCount(4);
        when(commentMapper.selFeedCommentAll(dto1)).thenReturn(feed1comment);
        when(commentMapper.selFeedCommentAll(dto2)).thenReturn(feed2comment);

        FeedSelDto dto = new FeedSelDto();
        List<FeedSelVo> result = service.feedSel(dto);

        assertEquals(list, result);

        for (int i = 0; i < list.size(); i++) {
            List<FeedSelVo> vo = list;
            assertNotNull(vo.get(i).getPics());

            assertEquals(vo.get(i).getPics(), picsList.get(i));

            assertEquals(vo.get(i).getPics(), picsArr[i]);

            FeedSelVo vo1 = list.get(i);
            assertNotNull(vo1.getPics());

            List<String> pics = picsList.get(i);
            assertEquals(vo1.getPics(), pics);

            List<String> pics2 = picsArr[i];
            assertEquals(vo1.getPics(), pics2);
        }
        for (int i = 0; i < list.size(); i++) {
            List<FeedSelVo> vo = list;

            assertNotNull(vo.get(i).getComments());

            if(vo.get(i).getComments().size() == 4){
                commentList.get(i).remove((commentList.get(i).size() -1 ));
                assertEquals(commentList.get(i).size(),vo.get(i).getComments().size());
            }

            for (int j = 0; j < vo.get(i).getComments().size() ; j++) {
                assertEquals(commentList.get(i).get(j), vo.get(i).getComments().get(j));
            }


            FeedSelVo vo1 = list.get(i);
            assertNotNull(vo1.getComments());

            List<FeedCommentSelVo> comments = commentList.get(i);
            assertEquals(vo1.getComments(), comments);

            for (int j = 0; j < vo1.getComments().size() ; j++) {
                assertEquals(commentList.get(i).get(j), vo1.getComments().get(j));
            }
        }

        List<FeedCommentSelVo> commentsResult1 = list.get(0).getComments();
        assertEquals(feed1comment, commentsResult1, "ifeed(1) 댓글 체크");
        assertEquals(1, list.get(0).getIsMoreComment(), "ifeed(1) isMoreComment 체크");
        assertEquals(3,list.get(0).getComments().size());
        assertEquals(3,feed1comment);
        List<FeedCommentSelVo> commentsResult2 = list.get(1).getComments();
        assertEquals(feed2comment, commentsResult2, "ifeed(2) 댓글 체크");
        assertEquals(1, list.get(1).getIsMoreComment(), "ifeed(2) isMoreComment 체크");
        assertEquals(3,list.get(1).getComments().size());
        assertEquals(3,feed2comment);
    }

    @Test
    void getFeedCommentAll() {
    }

    @Test
    void delFeed() {
    }

    @Test
    void toggleFeedFav() {
    }

    @Test
    void postComment() {
    }
}