package com.example.simpledms.security.services;

import com.example.simpledms.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * packageName : com.example.simplelogin.security.services
 * fileName : UserDetailsImp
 * author : ds
 * date : 2022-11-29
 * description : Spring Security 의 인증 대상 객체 (*****)
 *   UserDetails(인터페이스) : Spring Security 인증을 도입하고 싶은 대상에(모델) 대해서
 *                           UserDetails 상속받아서 사용하도록 권고
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         ds          최초 생성
 */
// 인증대상 : User 관련된 속성/함수
// UserDetails 인터페이스의 속성/함수
public class UserDetailsImpl implements UserDetails {

//    시리얼 번호 상수
    private static final long serialVersionUID = 1L;

    private Long id; // 시퀀스 id(개발자 속성)
    private String username; // Spring Security 제공하는 속성
    private String email; // 개발자 속성

//    json 속성 변환시 아래 속성은 무시
    @JsonIgnore
    private String password; //  Spring Security 제공하는 속성

//    권한관련 속성 : Spring Security 제공하는 속성
//    <? extends GrantedAuthority> : <자료형들> , GrantedAuthority 상속받은 자식클래스 타입도 허용
//    GrantedAuthority :
//         Spring Security 에서 권한관리를(ROLE_USER, ROLE_ADMIN 등) 목적으로 만든 클래스
    private Collection<? extends GrantedAuthority> authorities;

//    생성자
    public UserDetailsImpl(Long id, String username,
                           String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

//    공용 빌더 함수
//    매개변수 : user
//    설명 : 권한을 받아서 UserDetailsImpl 객체를 만드는 빌더함수
    public static UserDetailsImpl build(User user) {

//        매개변수 User의 권한 추출하기
//        role.getName().name() : ERole ( ROLE_USER, ROLE_ADMIN )
//        GrantedAuthority : security 에서 제공하는 권한관리 인터페이스
//         구현클래스 종류 : SimpleGrantedAuthority 등
//        ex) 아래 결과 : "ROLE_USER"  -> GrantedAuthority 타입의 권한으로 변경하는 부분
//                        -> 나중에 스프링 권한관리 기능을 이용할 수 있음
        List<GrantedAuthority> authorities =
//                user.getRoels : ROLE_USER, ROLE_ADMIN
                user.getRole().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList());
//        User == UserDetailsImpl
//        User role 은 문자열 : ROLE_USER 등
//        UserDetailsImpl role 은 GrantedAuthority 형태의 타입 (내부적으로 ROLE_USER 가 들어가있음)
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

//    개발자 만든 속성의 getter
    public Long getId() { return id;}
    public String getEmail() { return email; }

//    security 에서 제공하는 속성의 getter
//    권한관리 사용(재정의)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

//    사용
    @Override
    public String getPassword() {
        return password;
    }

//    사용
    @Override
    public String getUsername() {
        return username;
    }

//    계정이 만료되지 않았는지를 체크하는 함수
//    리턴값 : true 면 만료되지 않았음
//    true : 계정에 만료기간 없음으로 강제 설정
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

//    용도 : 계정이 잠겨있지 않는지를 체크
//    true : 계정이 잠겨있지 않음을 의미 ( 강제 설정 )
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

//    Credentials : 패스워드
//    용도 : 패스워드가 만료되지 않았음을 체크하는 함수
//    true : 만료 기능 없음 강제 설정
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

//    용도 : 계정이 사용할 수 있는 계정인지 여부를 체크하는 함수
//    true : 무조건 사용할 수 있게 하는 강제 설정
    @Override
    public boolean isEnabled() {
        return true;
    }

//    equals 재정의
//    용도 : id 가 같으면 같은 객체임을 나타내는 함수
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(id, that.id);
    }
}








