package com.greengram.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)//엔터티 적용 전에 콜백으로AuditingEntityListener를 호출해 공통적으로 처리
//@EntityListeners(AuditingEntityListener.class) 날짜 시간 관리
public class BaseEntity extends CreatedAtEntity {
    @LastModifiedDate //온 업데이트 옵션
    private LocalDateTime updatedAt;
}
