package com.greengram.greengram4.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greengram.greengram4.CharEncodingConfig;
import com.greengram.greengram4.common.ResVo;
import com.greengram.greengram4.feed.model.FeedInsDto;
import com.greengram.greengram4.feed.model.FeedSelVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@MockMvcConfig //UTF-8 가능하도록 직접 만들었음
@Import(CharEncodingConfig.class) //UTF-8 가능하도록 직접 만들었음
@WebMvcTest(FeedController.class) //빈등록된 컨트롤러를 객체화시킴
class FeedControllerTest {

    @Autowired
    private MockMvc mvc; //get post 등등 주소값과 메소드를 요청할 수 있는 역할
    @Autowired
    private ObjectMapper mapper; //객체를 json형태로 바꿔줄때 사용, 반대도 사용
    @MockBean //아무것도 값을 넣지않고 리턴하면 리턴해주는 타입의 디폴드값을 리턴함
    private FeedService service; //컨트롤러를 객체화(위의 webmvctest어노테이션)하려면 서비스가 객체화되야 되서 목빈 사용



    @Test
    void postFeed() throws Exception {
        ResVo result = new ResVo(5);
        //when(service.postFeed(any())).thenReturn(result);
        //given(service.postFeed(any())).willReturn(result);

        FeedInsDto dto = new FeedInsDto();
        String json = mapper.writeValueAsString(dto); //바디 부분 dto객체를 제이슨으로 변환하여 받음
        System.out.println(json);

        mvc.perform(
                MockMvcRequestBuilders
                    .post("/api/feed")  //방식 post ,get 등등
                    .contentType(MediaType.APPLICATION_JSON) //데이터를 제이슨으로 날리면 필수
                        //없으면 폼form 데이터 형식이라고 생각함
                    .content(json)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(result)))
                .andDo(print());

                verify(service).postFeed(any());
    }



    @Test
    void getFeed() throws Exception{
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("page", "2");
        params.add("loginedIuser","4");
        //쿼리스트링 대신 가능

        List<FeedSelVo> list = new ArrayList<>();
        FeedSelVo vo = new FeedSelVo();
        vo.setContents("안녕하세요");
        vo.setIfeed(1);
        vo.setLocation("아따맘마");

        FeedSelVo vo1 = new FeedSelVo();
        vo1.setContents("감사해요");
        vo1.setIfeed(2);
        vo1.setLocation("아따맘마");

        list.add(vo);
        list.add(vo1);

        //given(service.feedSel(any())).willReturn(list);

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/feed")
                        .params(params)
        ).andDo(print())
         .andExpect(content().string(mapper.writeValueAsString(list)));

        //verify(service).feedSel(any());
    }

    @Test
    void delFeed() throws Exception {
        ResVo result = new ResVo(5);

        given(service.delFeed(any())).willReturn(result);

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/feed")
        ).andDo(print())
         .andExpect(status().isOk())
         .andExpect(content().string(mapper.writeValueAsString(result)));

        verify(service).delFeed(any());
    }

    @Test
    void toggleFeedFav() {
    }

    @Test
    void postComment() {
    }

    @Test
    void getFeedCommentAll() {
    }
}