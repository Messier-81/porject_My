// FaqDataService.js
// 목적 : Faq 정보를 axios 로 CRUD 를 하는 공통 함수들 정의
import http from "../http-common";

class FaqDataService {
    // 모든 부서정보 조회 요청 함수
    getAll(searchSelect, searchKeyword, page, size) {
        // get 방식 통신 요청 -> @GetMapping("/api/faq")
        return http.get(`/faq?searchSelect=${searchSelect}&searchKeyword=${searchKeyword}&page=${page}&size=${size}`); 
    }

    // 부서번호로 조회 요청 함수
    // get 방식 통신 요청 -> @GetMapping("/api/faq/{no}"), @PathVariable
    get(no) {
        return http.get(`/faq/${no}`)
    }

    // 부서정보 생성(insert) 요청 함수
    // post 방식 통신 요청 -> @PostMapping("/api/faq"), @RequestBody
    create(data) {
        console.log(data);
        return http.post("/faq", data);
    }

    // 부서정보 수정(update) 요청 함수
    // put 방식 통신 요청 -> @PutMapping("/api/faq/{no}"), @RequestBody
    update(no, data) {
        return http.put(`/faq/${no}`, data);
    }

    // 부서정보 삭제(delete) 요청 함수
    // delete 방식 통신 요청 -> @DeleteMapping("/api/faq/deletion/{no}")
    //                        , @PathVariable  
    delete(no) {
        return http.delete(`/faq/deletion/${no}`);
    }

    // 부서정보 전체 삭제 요청 함수
    // delete 방식 통신 요청 -> @DeleteMapping("/api/faq/all")
    deleteAll() {
        return http.delete("/faq/all")
    }

}

export default new FaqDataService();