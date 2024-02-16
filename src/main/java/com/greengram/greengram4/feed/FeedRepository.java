package com.greengram.greengram4.feed;


import com.greengram.greengram4.entity.FeedEntity;
import com.greengram.greengram4.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// 해당 테이블, 그테이블의 pk타입
public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
    List<FeedEntity> findAllByUserEntityOrderByIfeedDesc(UserEntity userEntity, Pageable pageable);

}
