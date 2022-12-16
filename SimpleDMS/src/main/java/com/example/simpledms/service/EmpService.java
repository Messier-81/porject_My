package com.example.simpledms.service;

import com.example.simpledms.model.Dept;
import com.example.simpledms.model.Emp;
import com.example.simpledms.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.simpledms.service
 * fileName : EmpService
 * author : hyuk
 * date : 2022/11/02
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022/11/02         hyuk          최초 생성
 */
@Service
public class EmpService {
    @Autowired
    EmpRepository empRepository; // JPA CRUD 함수가 있는 인터페이스

    //    전체 조회 함수
    public Page<Emp> findAll(Pageable pageable) {
        Page<Emp> page = empRepository.findAll(pageable);

        return page;
    }

    //    ✅ id로 조회하는 함수

    public Optional<Emp> findById (int eno){
//        findById(기본키)
        Optional<Emp> optionalEmp = empRepository.findById(eno);
        return optionalEmp;
    }


    //    전체 삭제 함수
    public void removeAll() {
        empRepository.deleteAll();
    }


    //       사원 정보 저장 함수
    public Emp save(Emp emp){
        Emp emp2 = empRepository.save(emp);
        return emp2;
    }

    //       ✅ 부서 정보 삭제 함수
    public boolean removeById(int eno) {
//        existById(기본키) 있으면 삭제 실행 + true 리턴
        if (empRepository.existsById(eno) == true) {
            empRepository.deleteById(eno);
            return true;
        }
//        없으면 그냥 false 리턴
        return false;
    }


    //    ✅ ename like 검색 함수
    public Page<Emp> findAllByEnameContaining(String ename, Pageable pageable) {
        Page<Emp> page = empRepository.findAllByEnameContaining(ename, pageable);

        return page;
    }

}
