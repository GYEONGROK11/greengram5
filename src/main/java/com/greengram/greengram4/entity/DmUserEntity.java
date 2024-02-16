package com.greengram.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_dm_user")
public class DmUserEntity{
    @EmbeddedId
    private DmUserIds dmUserIds;

    @ManyToOne
    @MapsId("idm")
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED", name = "idm")
    private DmEntity dmEntity;

    @ManyToOne
    @MapsId("iuser")
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED",name = "iuser")
    private UserEntity userEntity;

}
