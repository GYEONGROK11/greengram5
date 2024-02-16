package com.greengram.greengram4.security.oauth2;

import com.greengram.greengram4.security.MyPrincipal;
import com.greengram.greengram4.security.MyUserDetails;
import com.greengram.greengram4.security.oauth2.userinfo.OAuth2UserInfo;
import com.greengram.greengram4.security.oauth2.userinfo.OAuth2UserInfoFactory;
import com.greengram.greengram4.user.UserMapper;
import com.greengram.greengram4.user.model.UserModel;
import com.greengram.greengram4.user.model.UserSelDto;
import com.greengram.greengram4.user.model.UserSignupProcDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class CustomeOAuth2UserService extends DefaultOAuth2UserService {
    private final UserMapper mapper;
    private final OAuth2UserInfoFactory factory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);  //오버라이딩으로 원래 자식의 메소드를 실행하지만 super.을 써서 부모의 메소드를 실행하겠다는 뜻
        //아래서 부터 커스텀
        try {
            return this.process(userRequest,user);
        } catch (AuthenticationException e){
            throw e;
        } catch (Exception e){
            throw new InternalAuthenticationServiceException(e.getMessage(),e.getCause());
        }

    }
    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user){
        SocialProviderType socialProviderType
                = SocialProviderType.valueOf(userRequest.getClientRegistration()
                                                        .getRegistrationId()
                                                        .toUpperCase()
                );
        Map<String,Object> attrs = user.getAttributes();
        OAuth2UserInfo oAuth2UserInfo = factory.getOAuth2UserInfo(socialProviderType, attrs);
        UserSelDto dto = UserSelDto.builder()
                .providerType(socialProviderType.name())
                .uid(oAuth2UserInfo.getId())
                .build();
        UserModel savedUser = mapper.selUser(dto); //null이면 카카오아이디로 회원가입하지 않은 상태
        if(savedUser == null){ //회원가입처리
            savedUser = signupUser(oAuth2UserInfo, socialProviderType);
        }

        MyPrincipal myPrincipal = MyPrincipal.builder()
                .iuser(savedUser.getIuser())
                .build();

        myPrincipal.getRoles().add(savedUser.getRole());

        return MyUserDetails.builder()
                .userModel(savedUser)
                .attributes(attrs)
                .myPrincipal(myPrincipal)
                .build();
    }
    private UserModel signupUser(OAuth2UserInfo oAuth2UserInfo, SocialProviderType socialProviderType){
        UserSignupProcDto dto = UserSignupProcDto.builder()
                .providerType(socialProviderType.name())
                .uid(oAuth2UserInfo.getId())
                .upw("socialLogin")
                .nm(oAuth2UserInfo.getName())
                .role("USER")
                .pic(oAuth2UserInfo.getImageUrl())
                .build();
        int result = mapper.insUser(dto);

        UserModel entity = new UserModel();
        entity.setIuser(dto.getIuser());
        entity.setUid(dto.getUid());
        entity.setRole(dto.getRole());
        entity.setNm(dto.getNm());
        entity.setPic(dto.getPic());
        return entity;
    }

}
