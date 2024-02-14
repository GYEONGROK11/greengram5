package com.greengram.greengram4.security;

import com.greengram.greengram4.user.model.UserEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class MyUserDetails implements UserDetails, OAuth2User {
                                     //로컬로그인  ,  소셜로그인
    private MyPrincipal myPrincipal;
    private Map<String,Object> attributes;
    private UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //호출후 값으로 권한 처리
        if (myPrincipal == null) {
            return null;
        }
        return  this.myPrincipal.getRoles().stream()
                //map은 형태를 변환하기 위해 같은 사이즈의 배열을 만든다
                //.map(SimpleGrantedAuthority::new)//가공이 없을때는 사용가능 지금은 ROLE_ADMIN으로 가공할 예정
                //.map(role -> { return new SimpleGrantedAuthority(role);})
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userEntity == null ? null : userEntity.getUid();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
