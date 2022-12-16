package com.example.simpledms.model.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * packageName : com.example.simplelogin.dto.request
 * fileName : LoginRequest
 * author : ds
 * date : 2022-11-29
 * description : 로그인 DTO
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         ds          최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

//    @NotBlank : 공백 또는 null 유효성 체크하는 @
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}










