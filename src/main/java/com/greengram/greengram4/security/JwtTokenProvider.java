package com.greengram.greengram4.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greengram.greengram4.common.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component //빈등록
@RequiredArgsConstructor
public class JwtTokenProvider { //토큰 만들어 줌

    private final ObjectMapper om; //잭슨 라이브러리에 있음 json을 객체로 반대도 가능
    private final AppProperties appProperties;
    private Key key;

    /*public JwtTokenProvider(@Value("${springboot.jwt.secret}") String secret
            , @Value("${springboot.jwt.header-scheme-name}") String headerSchemeName
            , @Value("${springboot.jwt.token-type}") String tokenType) {
        this.secret = secret;
        this.headerSchemeName = headerSchemeName;
        this.tokenType = tokenType;
        log.info("secret:{}", secret);
    }*/

    @PostConstruct //빈등록이 되고 di 받을것이 있을때 di 받고난 후(생성자로 받음) 메소드를 호출함
    //스프링이 켜질때 한번 메소드를 호출하고 싶다
    //빈등록 : 스프링컨테이너에 의해 객체생성됨
    public void init() {
        log.info("secret:{}", appProperties.getJwt().getSecret());
        byte[] keyBytes = Decoders.BASE64.decode(appProperties.getJwt().getSecret());
        log.info("KeysBytes:{}", keyBytes);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(MyPrincipal principal, long tokenValidMs) {//토큰 만들기
        //Date now = new Date();
        return Jwts.builder()
                //.issuedAt(now)
                .claims(createClaims(principal))//토큰
                .issuedAt(new Date(System.currentTimeMillis()))//발행시간
                .expiration(new Date(System.currentTimeMillis() + tokenValidMs))//만료시간
                .signWith(this.key)//암호화
                .compact();
    }

    private Claims createClaims(MyPrincipal principal){//항목이 추가 될 때마다 add 계속함
        //그래서 json 문자열로 바꿔서 통째로 넣음
        try{
        String json =om.writeValueAsString(principal);

        return Jwts.claims()
                .add("user",json)
                ///.add("iuser", principal.getIuser())
                //.add("role",principal.getRoles())
                .build();
        }catch (Exception e){
            return null;
        }

    }

    public String resolveToken(HttpServletRequest req) { //Http - 요청이 오면 무조건 만들어지는 객체 - 요청시 날아오는 모든 정보가 담겨있음
        //
        String auth = req.getHeader(appProperties.getJwt().getHeaderSchemeName());
        if (auth == null) {
            return null;
        }
        //Bearer Askladdsafghknalerkghnalerkgnalsdf124
        if (auth.startsWith(appProperties.getJwt().getTokenType())) {//auth - 인증 , startswith - Bearer로 시작하는지

            return auth.substring(appProperties.getJwt().getTokenType().length()).trim();//length는 6 bearer
            //substring은 6개 뒤( Bearer 뒤) 공백부터 끝까지 - 그것을 trim(문자열 앞뒤 공백 제거)으로 잘라냄
        }
        return null;
    }

    public boolean isValidateToken(String token){ //만료시간 체크
        try {
            return !getAllClaims(token).getExpiration().before(new Date());
            //만료기간이 현재 시간보다 전이면 false
            //만료기간이 현재 시간보다 후면 true
        } catch (Exception e){
            return false;
        }
    }
    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = getUserDetailsFromToken(token);
        return userDetails == null
                ? null
                : new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }
    private UserDetails getUserDetailsFromToken(String token){
        try{
            Claims claims = getAllClaims(token);
            String json = (String)claims.get("user");
            MyPrincipal myPrincipal = om.readValue(json, MyPrincipal.class);
            return MyUserDetails.builder()
                    .myPrincipal(myPrincipal)
                    .build();
        } catch (Exception e){
            return null;
        }
    }
}
