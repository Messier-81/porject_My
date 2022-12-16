package com.example.simpledms.service;

import com.example.simpledms.model.Qna;
import com.example.simpledms.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.example.jpaexam.service.exam01
 * fileName : QnaService
 * author : ds
 * date : 2022-10-20
 * description : 부서 업무 서비스 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
@Service
public class QnaService {

    @Autowired
    QnaRepository qnaRepository; // JPA CRUD 함수가 있는 인터페이스

    //    전체 조회 함수( 페이징 처리 )
    public Page<Qna> findAll(Pageable pageable) {
        Page<Qna> page = qnaRepository.findAll(pageable);

        return page;
    }

    //    전체 삭제 함수
    public void removeAll() {
        qnaRepository.deleteAll(); // 전체 삭제
    }

    //   부서 정보 저장/수정 함수
    public Qna save(Qna qna) {

        Qna qna2 = qnaRepository.save(qna);

        return qna2;
    }

    //    부서번호로 조회하는 함수
    public Optional<Qna> findById(int qno) {
//        findById(기본키속성)
        Optional<Qna> optionalQna = qnaRepository.findById(qno);

        return optionalQna;
    }

    // 부서번호(no)로 삭제하는 함수
    public boolean removeById(int qno) {
//        existsById(기본키) 있으면 삭제 실행 + true 리턴
        if(qnaRepository.existsById(qno) == true) {
            qnaRepository.deleteById(qno);
            return true;
        }

//        없으면 그냥 false 리턴
        return false;
    }

    //    question(질문) like 검색 함수 ( 페이징 처리 )
    public Page<Qna> findAllByQuestionerContaining(String questioner, Pageable pageable) {
        Page<Qna> page = qnaRepository.findAllByQuestionerContaining(questioner, pageable);

        return page;
    }

    //    questioner(질문자) like 검색 함수 ( 페이징 처리 )
    public Page<Qna> findAllByTitleContaining(String title, Pageable pageable) {
        Page<Qna> page = qnaRepository.findAllByTitleContaining(title, pageable);

        return page;
    }
}









