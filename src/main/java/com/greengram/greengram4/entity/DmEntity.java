package com.greengram.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_dm")
public class DmEntity extends CreatedAtEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long idm;

    @Column(length = 2000)
    private String lastMsg;

    @LastModifiedDate
    @Column
    private LocalDateTime lastMsgAt;
}
