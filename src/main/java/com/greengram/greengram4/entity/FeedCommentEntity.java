package com.greengram.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_feed_comment")
public class FeedCommentEntity extends BaseEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED",name = "ifeed_comment")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토인클리먼트
    private Long ifeedComment;

    @ManyToOne
    @JoinColumn(name = "iuser",nullable = false)
    private UserEntity UserEntity;

    @ManyToOne
    @JoinColumn(name = "ifeed",nullable = false)
    private FeedEntity feedEntity;

    @Column(length = 500, nullable = false)
    private String comment;

}
