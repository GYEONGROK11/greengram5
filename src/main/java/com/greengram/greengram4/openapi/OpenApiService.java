package com.greengram.greengram4.openapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.greengram.greengram4.common.OpenApiProperties;
import com.greengram.greengram4.openapi.model.ApartmentTransactionDetailDto;
import com.greengram.greengram4.openapi.model.ApartmentTransactionDetailVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.util.List;




@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final OpenApiProperties openApiProperties;

    public List<ApartmentTransactionDetailVo> getApartmentTransactionList(ApartmentTransactionDetailDto dto) throws Exception{


        DefaultUriBuilderFactory factory =
                new DefaultUriBuilderFactory(openApiProperties.getApartment().getBaseUrl());
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);//web클라이언트 방식 - 인코딩 한번더 하지말라는 설정

        //web클라이언트 방식   인코딩이 자동으로됨
        WebClient webClient = WebClient.builder()
                .uriBuilderFactory(factory)
                .filters(exchangeFilterFunctions -> exchangeFilterFunctions.add(logRequest())) //로그보기
                //.clientConnector(new JettyClientHttpConnector(httpClient))
                .baseUrl(openApiProperties.getApartment().getBaseUrl())
                .build();

        String data = webClient.get().uri(uriBuilder -> {
                    UriBuilder ub = uriBuilder
                            .path(openApiProperties.getApartment().getDataUrl())
                            .queryParam("DEAL_YMD", dto.getDealYm())
                            .queryParam("LAWD_CD", dto.getLawdCd())
                            .queryParam("serviceKey", openApiProperties.getApartment().getServiceKey());
                    if (dto.getPageNo() > 0) {
                        ub.queryParam("pageNo", dto.getPageNo());
                    }
                    if (dto.getNumOfRows() > 0) {
                        ub.queryParam("numOfRows", dto.getNumOfRows());
                    }
                    return ub.build();
                }
        ).retrieve()
         .bodyToMono(String.class)
         .block();  //비동기를 동기로 바꾼다 통신이 끝날 때 까지 기다림
                    //비동기 상태면 통신이 끝나지 않아도 json에 데이터가 없어도 코드가 실행됨
        log.info("data : {}", data);
        ObjectMapper om  =new XmlMapper() //xml파일을 json으로
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                        ,false);
        JsonNode jsonNode = om.readTree(data);
        List<ApartmentTransactionDetailVo> list = om.convertValue(
                jsonNode.path("body")
                        .path("items")
                        .path("item")
                , new TypeReference<List<ApartmentTransactionDetailVo>>() {});
        return list;
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (log.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Request: \n");
                //append clientRequest method and url
                clientRequest
                        .headers()
                        .forEach((name, values) -> values.forEach(value -> log.info(value)));
                log.debug(sb.toString());
            }
            return Mono.just(clientRequest);
        });
    }
}
