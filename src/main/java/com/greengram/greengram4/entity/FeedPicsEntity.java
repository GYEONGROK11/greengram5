package com.greengram.greengram4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

@Data
@Entity
@Table(name = "t_feed_pics")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedPicsEntity extends CreatedAtEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토인클리먼트
    private Long ifeedPics;

    @ManyToOne
    @JoinColumn(name = "ifeed",nullable = false)
    private FeedEntity feedEntity;

    @Column(length = 2100,nullable = false)
    private String pic;

}
