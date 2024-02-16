package com.greengram.greengram4.user;

import com.greengram.greengram4.common.ProviderTypeEnum;
import com.greengram.greengram4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 해당 테이블, 그테이블의 pk타입
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByProviderTypeAndUid(ProviderTypeEnum providerType, String uid);

}
