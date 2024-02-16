package com.greengram.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_user_follow")
public class UserFollowEntity extends CreatedAtEntity {
    @EmbeddedId
    private UserFollowIds userFollowIds;

    @ManyToOne
    @MapsId("fromIuser")
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED",name = "from_iuser")
    private UserEntity fromUserEntity;

    @ManyToOne
    @MapsId("toIuser")
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED",name = "to_iuser")
    private UserEntity toUserEntity;
}
