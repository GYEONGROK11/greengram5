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
@MappedSuperclass // 나를 상속받으면 내가 가지고있는 매핑자료도 사용가능
@EntityListeners(AuditingEntityListener.class) // 엔터티 적용 전에 콜백으로 AuditingEntityListener를 호출해 공통적으로 처리
public class CreatedAtEntity {
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

}
