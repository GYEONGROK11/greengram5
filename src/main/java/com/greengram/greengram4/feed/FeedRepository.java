package com.greengram.greengram4.feed;


import com.greengram.greengram4.entity.FeedEntity;
import com.greengram.greengram4.entity.UserEntity;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// 해당 테이블, 그테이블의 pk타입
public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
    @EntityGraph(attributePaths = {"userEntity"}) //그래프 탐색으로 같이 가져올 멤버필드  // 없으면 셀렉 두번됨
    List<FeedEntity> findAllByUserEntityOrderByIfeedDesc(UserEntity userEntity, Pageable pageable);

}
