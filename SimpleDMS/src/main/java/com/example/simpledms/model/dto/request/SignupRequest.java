package com.example.simpledms.model.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * packageName : com.example.simplelogin.dto.request
 * fileName : SignupRequest
 * author : ds
 * date : 2022-11-29
 * description : 회원가입 DTO
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         ds          최초 생성
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank               // 공백 또는 null 허용 안함
    @Size(min = 3, max= 20) // 자리수가 3 ~ 20 까지만 허용
    private String username; // 로그인id

    @NotBlank               // 공백 또는 null 허용 안함
    @Size(min = 6, max= 40) // 자리수가 6 ~ 40 까지만 허용
    private String password;

    @NotBlank               // 공백 또는 null 허용 안함
    @Size(max= 50)          // 자리수가 50 까지만 허용
    @Email                  // email 형식이 맞는지 체크하는 @
    private String email;

    private Set<String> role; // 역할 ( ROLE_USER, ROLE_ADMIN )

    private boolean changePwd; // 패스워드 변경시 사용할 속성
}









