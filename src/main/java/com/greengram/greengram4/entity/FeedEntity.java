package com.greengram.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_feed")
public class FeedEntity extends BaseEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토인클리먼트
    private Long ifeed;

    @ManyToOne
    @JoinColumn(name = "iuser",nullable = false)
    private UserEntity UserEntity;

    @Column(length = 1000)
    private String contents;

    @Column(length = 30)
    private String location;

    @ToString.Exclude//string으로 변환시 얘는 제외하고 변환
    @OneToMany(mappedBy = "feedEntity",cascade = CascadeType.PERSIST)  //양방향설정으로 영속성 유지 picsRepository안만들어도됨
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList();

}
