package com.example.simpledms.model.dto.response;

/**
 * packageName : com.example.simpledms.dto.response
 * fileName : UserRoleDto
 * author : hyuk
 * date : 2022/11/30
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022/11/30         hyuk          최초 생성
 */
public interface UserRoleDto {
//    User 클래스 getter
    Long getId();
    String getUsername();
    String getEmail();
//    Role 클래스 getter
    Long getRid();
    String getName();
}
