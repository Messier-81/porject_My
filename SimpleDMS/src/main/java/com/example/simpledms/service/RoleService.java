package com.example.simpledms.service;

import com.example.simpledms.model.ERole;
import com.example.simpledms.model.Role;
import com.example.simpledms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.example.simpledms.service
 * fileName : RoleService
 * author : hyuk
 * date : 2022/11/30
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022/11/30         hyuk          최초 생성
 */
@Service
public class RoleService {
    @Autowired
    RoleRepository repository;

    public Optional<Role> findByName(ERole name){
        return repository.findByName(name);
    }
}
