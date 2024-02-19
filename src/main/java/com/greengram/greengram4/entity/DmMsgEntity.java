package com.greengram.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_dm_msg")
public class DmMsgEntity extends CreatedAtEntity{
    @EmbeddedId //복합키 만들기
    private DmMsgIds dmMsgIds;

    @ManyToOne  //포링키
    @MapsId("idm")  //Ids에 있는 필드명, 없으면 컬럼 추가됨
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED", name = "idm")
    private DmEntity dmEntity;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity userEntity;

    @Column(length = 2000, nullable = false)
    private String msg;

}
