package com.example.simpledms.model.dto.response;

import java.util.List;

/**
 * packageName : com.example.simplelogin.dto.response
 * fileName : JwtResponse
 * author : ds
 * date : 2022-11-29
 * description : User + 웹토큰 응답 DTO
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         ds          최초 생성
 */
public class JwtResponse {

//    ------------ 웹토큰 속성
    private String token; // 웹토큰 속성
    private String type = "Bearer"; // 웹토큰 헤더 타입

//    ------------ User 속성
    private Long id; // User 시퀀스 id
    private String username; // User (로그인id)
    private String email;
    private List<String> roles; // ROLE_USER, ROLE_ADMIN 역할 (배열)

//    생성자
    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

//  TODO: 함수명 수정  Getter/Setter :
    public String getAccessToken() {
        return token;
    }

//    TODO: 함수명 수정
    public void setAccessToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    TODO: setRoles() 제거
    public List<String> getRoles() {
        return roles;
    }
}









