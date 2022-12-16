package com.example.simpledms.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * packageName : com.example.jpaexam.model
 * fileName : FAQ
 * author : ds
 * date : 2022-10-19
 * description : 부서 모델(== JPA : 엔티티(Entity)) 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-19         ds          최초 생성
 */
@Entity
@Table(name = "TB_FAQ")
@SequenceGenerator(
        name= "SQ_FAQ_GENERATOR"
        , sequenceName = "SQ_FAQ"
        , initialValue = 1
        , allocationSize = 1
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// null 인 컬럼 데이터는 sql 문 자동 생성시 제외시킴
@DynamicInsert
@DynamicUpdate
//  soft delete : 삭제하는 척만 하기 (화면에서는 안보이고, db는 데이터를 삭제하지 않음)
//  법정 의무 보관 기간을 위해 실제 데이터를 삭제하지 않음
//  사용법 1) @SQLDelete(sql="update문") : delete 문이 실행되지 않고, 매개변수의 update문이 실행되게함
//       2) @Where(clause="조건") : 대상클래스 붙이면 sql문 실행 시 강제 조건을 부여함
@Where(clause = "DELETE_YN = 'N'")
@SQLDelete(sql="UPDATE TB_FAQ SET DELETE_YN = 'Y', DELETE_TIME = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE NO = ?")
public class Faq extends BaseTimeEntity {
    //    부서 번호 : dno
//    @Id : 기본키
//    시퀀스 사용 : ORACLE / POSTGRE 등
//    MYSQL /MARIA DB -> increment 이용
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
                   , generator = "SQ_FAQ_GENERATOR"
    )
    private Integer no;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String title;
    @Column(columnDefinition = "VARCHAR2(3000)")
    private String content;
}










