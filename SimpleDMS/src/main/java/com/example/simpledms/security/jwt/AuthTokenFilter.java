package com.example.simpledms.security.jwt;

import com.example.simpledms.security.services.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName : com.example.simplelogin.security.services
 * fileName : AuthTokenFilter ( 웹토큰 필터 클래스 )
 * author : ds
 * date : 2022-11-29
 * description : spring security 에는 웹토큰 필터가 기본적으로 제공하지 않으므로
 *              아래 클래스를 작성해서 사용함 ( OncePerRequestFilter 상속해서 작성 )
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         ds          최초 생성
 */
// JWT 토큰 인증을 위한 필터 , 스프링에 기본적으로 없으므로 만들어 주어야함 ( 기본 : 세션 필터 )
// OncePerRequestFilter : 요청 당 반드시 한번만 인증/인가 로직 실행하게 보장함
// 예) 요청에 대해 인증/권한체크 후(서브 요청 1) 특정 url로 리다이렉트할때(서브 요청 2) 보통은 인증/권한체크가 2번 일어남 이때
//     마지막은 불필요하므로 한번만 인증/권한체크가 일어나게 보장하게 만들어주는 인터페이스
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;  // 웹토큰 유틸리티 클래스(생성 등)

  @Autowired
  private UserDetailsServiceImpl userDetailsService; // DB 체크

//  Json Web Token 필터 만들어 SecurityContextHolder 에 새로운 JWT 필터 저장
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
//      클라이언트에서 전송된 데이터 헤더정보를 체크해서
//      웹토큰을 추출하는 함수 호출(parseJwt)
      String jwt = parseJwt(request);

//      TODO: 아래 절차 중요(*****)
//      1. 웹토큰 유효성 체크해서 : ok
//      2. 유효하면 DB에서 유저 있는 지 조회 : ok
//      3. 조회된 유저를 인증된 유저로 강제 적용해서(true)
//      4. 홀더에(security에서 인증된 유저만 관리하는 공간) 넣음
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//        웹토큰에서 유저 id 꺼냄
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

//        2. 유저 id로 db 조회해서 userDetails에 넣음
//        User 객체 role : 문자열 --> UserDetails 객체 role : GrantedAuthrity 타입 속성
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

//      3. 인증된 객체로 강제 설정 :
//         new UsernamePasswordAuthenticationToken()
//            매개변수 3개짜리 생성자 효출하면 강제 인증 성공 authenticated = true 로 설정됨
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

//      4. 인증된 authentication 객체를 홀더에 넣어둠
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

//    필터체인으로 연결하여 줍니다.
//    체인에 필터를 실행하고 체인의 가장 마지막에는 클라이언트가 요청한 최종 자원이 위치
    filterChain.doFilter(request, response);  // 필터 실행
  }

//  네트웍으로 전송된 헤더 데이터에 "Bearer" 있고
//  "Authorization" 다음 문자열이 있으면 7부터 헤더의 길이만큼 잘라서 리턴함
//  용도 : spring security에서 날라온 요청(Vue) 데이터에 아래 정보가 있는지 확인하고
//        웹토큰만 잘라내서 리턴하는 함수
  private String parseJwt(HttpServletRequest request) {

    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length()); // 웹토큰 잘라내기
    }

    return null;
  }
}
