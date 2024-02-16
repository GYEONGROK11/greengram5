package com.greengram.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_dm_msg")
public class DmMsgEntity extends CreatedAtEntity{
    @EmbeddedId
    private DmMsgIds dmMsgIds;

    @ManyToOne
    @MapsId("idm")
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED", name = "idm")
    private DmEntity dmEntity;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity userEntity;

    @Column(length = 2000, nullable = false)
    private String msg;

}
