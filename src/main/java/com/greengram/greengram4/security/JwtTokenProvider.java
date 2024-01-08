package com.greengram.greengram4.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component //빈등록
public class JwtTokenProvider { //토큰 만들어 줌

    private final String secret;
    private final String headerSchemeName;
    private final String tokenType;
    private Key key;

    public JwtTokenProvider(@Value("${springboot.jwt.secret}") String secret
    ,@Value("${springboot.jwt.header-scheme-name}") String headerSchemeName
    ,@Value("${springboot.jwt.token-type}")String tokenType) {
        this.secret = secret;
        this.headerSchemeName = headerSchemeName;
        this.tokenType = tokenType;
        log.info("secret:{}", secret);
    }

    @PostConstruct //빈등록이 되고 di 받을것이 있을때 di 받고난 후(생성자로 받음) 메소드를 호출함
    //스프링이 켜질때 한번 메소드를 호출하고 싶다
    //빈등록 : 스프링컨테이너에 의해 객체생성됨
    public void init() {
        log.info("secret:{}", secret);
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        log.info("KeysBytes:{}", keyBytes);
        this.key = Keys.hmacShaKeyFor(keyBytes);

    }

    public String generateToken(MyPrincipal principal, long tokenValidMs) {//토큰 만들기
        Date now = new Date();
        return Jwts.builder()
                //.issuedAt(now)
                .claims(createClaims(principal))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+tokenValidMs))
                .signWith(this.key)
                .compact();
    }

    private Claims createClaims(MyPrincipal principal){
        return Jwts.claims()
                .add("iuser",principal.getIuser())
                .build();
    }
    public String resolveToken(HttpServletRequest req){ //Http - 요청이 오면 무조건 만들어지는 객체 - 요청시 날아오는 모든 정보가 담겨있음
        //
        String auth =req.getHeader(headerSchemeName);
        if(auth == null){return null;}
        //Bearer Askladdsafghknalerkghnalerkgnalsdf124
        if(auth.startsWith(tokenType)){
            return auth.substring(tokenType.length()).trim();
        }
        return null;
    }
}
