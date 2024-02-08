package com.greengram.greengram4.user.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSelDto {
    private String uid;
    private String providerType;
    private int iuser;
}
