package com.example.simpledms.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * packageName : com.example.jpaexam.model
 * fileName : Emp
 * author : ds
 * date : 2022-10-20
 * description : 사원 모델(==엔티티)
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
@Entity
@Table(name = "TB_EMP")
@SequenceGenerator(
        name= "SQ_EMP_GENERATOR"
        , sequenceName = "SQ_EMP"
        , initialValue = 1
        , allocationSize = 1
)
// lombok 라이브러리 어노테이션
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
// null 무시하고 sql 문 자동생성하는 어노테이션
@DynamicInsert
@DynamicUpdate
@Where(clause = "DELETE_YN = 'N'")
@SQLDelete(sql="UPDATE TB_EMP SET DELETE_YN = 'Y', DELETE_TIME = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE ENO = ?")
public class Emp extends BaseTimeEntity {

    //          eno (int) , 시퀀스 초기값 1, 1증가, 기본키 설정
//          ename (String)
//          job (String)
//          manager (int)
//          hiredate (String)
//          salary  (int)
//          commission (int)
//          dno (int)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "SQ_EMP_GENERATOR"
    )
    @Column(columnDefinition = "NUMBER")
    private Integer eno;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String ename;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String job;

    @Column(columnDefinition = "NUMBER")
    private Integer manager;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String hiredate;

    @Column(columnDefinition = "NUMBER")
    private Integer salary;

    @Column(columnDefinition = "NUMBER")
    private Integer commission;

    @Column(columnDefinition = "NUMBER")
    private Integer dno;
}












