package com.greengram.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Embeddable
@EqualsAndHashCode //같은레코드 셀럭시 true가 되게끔 함
public class DmMsgIds implements Serializable { //시리얼라자블 필수
    private Long idm;
    @Column(columnDefinition = "BIGINT UNSIGNED") //외래키를 안건 새로 만들 것이라 옵션 여기서 줌
    private Long seq;
}

