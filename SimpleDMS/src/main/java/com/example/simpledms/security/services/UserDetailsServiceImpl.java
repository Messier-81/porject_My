package com.example.simpledms.security.services;

import com.example.simpledms.model.User;
import com.example.simpledms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName : com.example.simplelogin.security.services
 * fileName : UserDetailsServiceImpl
 * author : ds
 * date : 2022-11-29
 * description : username 정보가 DB 에 있는지 체크하고 있으면 UserDetails 객체를 생성하는 클래스
 *               (*****)
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         ds          최초 생성
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

//    용도 : username 이 DB 에 있는지 체크하고 있으면 UserDetails 객체를 생성하는 함수
    @Override
    @Transactional // JPA 사용하시면 조회에 @Transactional 성능향상이 조금 있음
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        username(로그인id) 에 해당하는 유저가 DB에 있는지 확인
//         return : 있으면 user 객체
//                  없으면 Error 메세지 출력 :"User Not Found with username: " + username
        User user = userRepository.findByUsername(username)
                .orElseThrow(()
                        -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user); // 유저 객체 생성
    }
}








