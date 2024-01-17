package com.greengram.greengram4.feed;


import com.greengram.greengram4.BaseIntegrationTest;
import com.greengram.greengram4.common.ResVo;
import com.greengram.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedIntegrationTest extends BaseIntegrationTest {
    @Test
    @Rollback(true)//롤백 껐다 키는
    public void postFeed() throws Exception {
        FeedInsDto dto = new FeedInsDto();
        dto.setIuser(4);
        dto.setContents("안녕하세요");
        dto.setLocation("하궝");
        List<String> pics = new ArrayList<>();
        pics.add("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202306/25/488f9638-800c-4bac-ad65-82877fbff79b.jpg");
        pics.add("https://health.chosun.com/site/data/img_dir/2023/07/17/2023071701753_0.jpg");
        //dto.setPics(pics);
        String json = om.writeValueAsString(dto); //바디 부분 dto객체를 제이슨으로 변환하여 받음
        System.out.println(json);

        MvcResult mr = mvc.perform(
                MockMvcRequestBuilders
                    .post("/api/feed")  //방식 post ,get 등등
                    .contentType(MediaType.APPLICATION_JSON) //데이터를 제이슨으로 날리면 필수
                 //없으면 폼form 데이터 형식이라고 생각함
                 .content(json)
                )
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
        String content = mr.getResponse().getContentAsString(); //respons라는 멤버필드가있고 그걸 가져와서 json 형태임
        ResVo vo = om.readValue(content, ResVo.class); //json을 오브젝트 - 최종적으로 ResVo로 바꿈
        assertEquals(true,vo.getResult()>0);
        //json 형식으로 넘어옴 { result : 아이피드값 } - 기대값
    }

    @Test
    @Rollback(false)
    public void delFeed() throws Exception{
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("iuser", "3");
        params.add("ifeed","98");
        MvcResult mr = mvc.perform(
                        MockMvcRequestBuilders
                                //.delete("/api/feed?ifeed={ifeed}&iuser={iuser}","98","3")
                                .delete("/api/feed")
                                .params(params)
                ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = mr.getResponse().getContentAsString();
        //{ result : 1 }
        //respons라는 멤버필드가있고 그걸 가져와서 json 형태로 저장
        ResVo vo = om.readValue(content, ResVo.class); //json을 오브젝트 - 최종적으로 ResVo로 바꿈
        assertEquals(1,vo.getResult());

    }




}

