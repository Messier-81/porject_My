<template>
    <div>
        <!-- 최상단 시작 -->
        <section class="py-5 overflow-hidden bg-warning" id="home">
            <div class="container">
                <div class="row flex-center">
                    <div class="col-md-5 col-lg-6 order-0 order-md-1 mt-8 mt-md-0">
                        <a class="img-landing-banner" href="#!"><img class="img-fluid"
                                src="assets/img/gallery/hero-header.png" alt="hero-header" /></a>
                    </div>
                    <div class="col-md-7 col-lg-6 py-8 text-md-start text-center">
                        <h1 class="display-1 fs-md-5 fs-lg-6 fs-xl-8 text-light">
                            여기는 질문과답변 <br />
                            페이지 입니다
                        </h1>
                        <h1 class="text-800 mb-5 fs-4">최상단만 제작</h1>
                    </div>
                </div>
            </div>
        </section>
        <!-- 최상단 끝 -->

        <!-- TODO: qna 시작 -->
        <!-- Contact Start -->
        <div class="container mt-2 mb-2">
            <h1>1:1 문의 게시판</h1>
            <!-- search 관련 div 시작 -->
            <div class="col-md-8 offset-2">
                <div class="input-group mb-3">
                    <!-- select box 추가 : v-model="searchSelect" -->
                    <div class="col-3">
                        <select class="form-select" v-model="searchSelect">
                            <option>선택</option>
                            <option>제목</option>
                            <option>작성자</option>
                        </select>
                    </div>

                    <!-- searchDname -> searchKeyword 변경 -->
                    <div class="col-8">
                        <input type="text" class="form-control" placeholder="Search by Question"
                            v-model="searchKeyword" />
                    </div>

                    <div class="input-group-append col-1">
                        <button class="btn btn-warning" type="button" @click="
              page = 1;
          retrieveQna();
                        ">
                            Search
                        </button>
                    </div>
                    <!--            Todo : 수정 끝 -->
                </div>
            </div>
            <!-- search 관련 div 끝 -->

            <!--    Todo : page 바 시작 주석 처리 -->
            <!-- <div class="col-md-12 offset-2">
                  <div class="mb-3">
                      Items per Page:
                      <select v-model="pageSize" @change="handlePageSizeChange($event)">
                          <option v-for="size in pageSizes" :key="size" :value="size">
                                 size : 3, 6, 9 
                              {{ size }}
                          </option>
                      </select>
                  </div>
  
                  <b-pagination v-model="page" :total-rows="count" :per-page="pageSize" prev-text="Prev" next-text="Next"
                      @change="handlePageChange"></b-pagination>
              </div> -->

            <!--    Todo : page 바 끝 주석처리 -->
            <div>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th class="table-active" style="width: 10%" scope="col">#</th>
                            <th class="table-active" style="width: 40%" scope="col">제목</th>
                            <th class="table-active" style="width: 20%" scope="col">작성자</th>
                            <th class="table-active" style="width: 20%" scope="col">작성일</th>
                            <th class="table-active" style="width: 10%" scope="col">수정/삭제</th>
                        </tr>
                    </thead>
                    <tbody v-for="(data, index) in qna" :key="index">
                        <tr>
                            <td>{{ data.qno }}</td>
                            <td>{{ data.title }}</td>
                            <td>{{ data.questioner }}</td>
                            <td>{{ data.insertTime }}</td>
                            <td>
                                <router-link :to="'/qna/' + data.qno"><span
                                        class="badge rounded-pill bg-warning text-dark">수정</span></router-link>
                            </td>
                        </tr>
                    </tbody>
                </table>



                <!-- <router-link to="/add-qna/">
            <span class="badge bg-warning text-dark">추가</span>
          </router-link> -->
                <!-- TODO: badge를 버튼으로 교체 -->
                <router-link class="offset-11" to="/add-qna/">
                    <button type="button" class="btn btn-warning btn-sm">
                        글쓰기
                    </button>
                </router-link>
            </div>
            <div class="overflow-auto offset-5">
                <b-pagination v-model="page" :total-rows="count" :per-page="pageSize" first-text="<<" last-text=">>"
                    prev-text="Prev" next-text="Next" @change="handlePageChange"></b-pagination>
            </div>
        </div>
        <!-- Contact End -->
        <!-- TODO: qna 끝 -->
    </div>
</template>
  
<script>
import QnaDataService from "../../../services/QnaDataService";
export default {
    data() {
        return {
            qna: [],
            searchKeyword: "",
            searchSelect: "선택",

            // 페이징을 위한 변수 정의
            page: 1, // 현재 페이지
            count: 0, // 전체 데이터 건수
            pageSize: 10, // 한페이지당 몇개를 화면에 보여줄지 결정하는 변수

            pageSizes: [3, 6, 9], // select box 에 넣을 기본 데이터
        };
    },
    methods: {
        // axios , 모든 정보 조회 요청 함수
        retrieveQna() {
            QnaDataService.getAll(
                this.searchSelect, // select box 선택된 값
                this.searchKeyword, // 검색어
                this.page - 1,
                this.pageSize
            )
                // 성공하면 .then() 결과가 전송됨
                .then((response) => {
                    const { qna, totalItems } = response.data; // springboot 의 전송된 맵 정보
                    this.qna = qna; // 스프링부트에서 전송한 데이터
                    this.count = totalItems; // 스프링부트에서 전송한 페이지정보(총 건수)
                    // 디버깅 콘솔에 정보 출력
                    console.log(response.data);
                })
                // 실패하면 .catch() 에 에러가 전송됨
                .catch((e) => {
                    console.log(e);
                });
        },
        // select box 값 변경시 실행되는 함수(재조회)
        handlePageSizeChange(event) {
            this.pageSize = event.target.value; // 한페이지당 개수 저장(3, 6, 9)
            this.page = 1;
            // 재조회 함수 호출
            this.retrieveQna();
        },
        // 페이지 번호 변경시 실행되는 함수(재조회)
        handlePageChange(value) {
            this.page = value; // 매개변수값으로 현재페이지 변경
            // 재조회 함수 호출
            this.retrieveQna();
        },
    },
    // 화면이 뜨자마자 실행되는 이벤트(라이프 사이클 함수) : mounted(), created()
    mounted() {
        this.retrieveQna(); // 화면 로딩시 전체 조회함수 실행
    },
};
</script>
  
<style>

</style>