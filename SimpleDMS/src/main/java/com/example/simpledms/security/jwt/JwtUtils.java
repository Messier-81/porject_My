package com.example.simpledms.security.jwt;

import com.example.simpledms.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * packageName : com.example.simplelogin.security.services
 * fileName : JwtUtils
 * author : ds
 * date : 2022-11-29
 * description : JWT(Json Web Token) 유틸리티 클래스
 *             ( 생성, 유효성 체크, 웹토큰에서 username 추출 함수 )
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         ds          최초 생성
 */
@Slf4j
@Component
public class JwtUtils {
//  LOG 출력하는 방법 : @Slf4j , 아래처럼 로그 호출해도 됨
//  사용시 : log.debug(문자열)
//  private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

//  @Value("${.properties_속성명}") : application.properties 파일에 정의된 속성을 가져오는 @
  @Value("${bezkoder.app.jwtSecret}")
  private String jwtSecret; // bezKoderSecretKey 값 저장 (비밀키 == 사설키)

  @Value("${bezkoder.app.jwtExpirationMs}")
  private int jwtExpirationMs; // 86400000 값이 저장됨

//  참고) JWT 토큰 구조 :
//  JWT 토큰 만들기 함수
  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

//    Json Web Token 구조 : 헤더(header).내용(payload).서명(signature)
//    헤더 : 토큰타입, 암호화 알고리즘이름
//    내용 : 데이터(subject(주체(이름))), 토큰발급대상(issuedAt), 만료기간(expiration), 토큰수령자
//    서명 : Jwts.builder().signWith(암호화알고리즘, 비밀키값)
//    생성 : Jwts.builder().compact()
    return Jwts.builder()
        .setSubject((userPrincipal.getUsername())) // 로그인ID
        .setIssuedAt(new Date()) // 발행일
//            만료일자 적용
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret) // 암호화 적용 서명
        .compact(); // 토큰 생성
  }

//  JWT 토큰에서 username(로그인ID) 꺼내기 함수
  public String getUserNameFromJwtToken(String token) {
//    웹토큰의 비밀키 + 토큰명을 적용해 body 안의 subject(주체(이름))에 접근해서 꺼냄
    return Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

//  JWT 웹토큰 유효성 체크 함수
//  디지털 서명이 위조 또는 훼손되었는지 확인하는 함수
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}
