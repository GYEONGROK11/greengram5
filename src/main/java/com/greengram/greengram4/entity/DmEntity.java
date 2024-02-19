package com.greengram.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_dm")
public class DmEntity extends CreatedAtEntity{
    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토인클리먼트
    @Column(columnDefinition = "BIGINT UNSIGNED") //UNSIGNED를 위한 하드코딩
    private Long idm;

    @Column(length = 2000)
    private String lastMsg;

    @LastModifiedDate
    @Column
    private LocalDateTime lastMsgAt;
}
