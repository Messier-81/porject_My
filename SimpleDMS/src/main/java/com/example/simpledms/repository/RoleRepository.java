package com.example.simpledms.repository;

import com.example.simpledms.model.ERole;
import com.example.simpledms.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * packageName : com.example.simplelogin.repository
 * fileName : RoleRepository
 * author : ds
 * date : 2022-11-29
 * description : Role 인터페이스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         ds          최초 생성
 */
@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer> {
//    권한명으로(Name) 조회하는 함수
    Optional<Role> findByName(ERole name);
}


