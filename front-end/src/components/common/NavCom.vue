<template>
  <div>
    <!-- 네비게이션 바 시작 -->
    <nav
      class="navbar navbar-expand-lg navbar-light bg-light fixed-top"
      data-navbar-on-scroll="data-navbar-on-scroll"
    >
      <div class="container">
        <!-- 로고명과 이미지 시작 -->
        <router-link to="/" class="navbar-brand d-inline-flex">
          <!-- <img
            class="h-25 d-inline-block"
            src="assets/img/gallery/tomato.png"
            alt="logo"/> -->
          <span class="text-1000 fs-3 fw-bold ms-2 text-gradient"
            >맛있는 토마토</span
          >
        </router-link>
        <!-- 로고명과 이미지 끝 -->

        <div class="collapse navbar-collapse" id="navbarNavDropdown">
          <ul class="navbar-nav">
            <li class="nav-item dropdown">
              <router-link
                to=""
                class="nav-link dropdown-toggle"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                음식점 리스트
              </router-link>
              <ul class="dropdown-menu">
                <li class="dropdown-header">지역별</li>
                <li>
                  <router-link to="/local/seoul" class="dropdown-item"
                    >서울</router-link
                  >
                </li>
                <li>
                  <router-link to="/local/gyeongi" class="dropdown-item"
                    >경기</router-link
                  >
                </li>
                <li>
                  <router-link to="/local/busan" class="dropdown-item"
                    >부산</router-link
                  >
                </li>
                <li><hr class="dropdown-divider"></li>
                <li class="dropdown-header">메뉴별</li>
                <li>
                  <router-link to="/" class="dropdown-item"
                    >한식</router-link
                  >
                </li>
                <li>
                  <router-link to="/" class="dropdown-item"
                    >중식</router-link
                  >
                </li>
                <li>
                  <router-link to="/" class="dropdown-item"
                    >일식</router-link
                  >
                </li>
                <li>
                  <router-link to="/" class="dropdown-item"
                    >양식</router-link
                  >
                </li>
                <li><hr class="dropdown-divider"></li>
                <li class="dropdown-header">테마별</li>
                <li>
                  <router-link to="/" class="dropdown-item"
                    >데이트</router-link
                  >
                </li>
                <li>
                  <router-link to="/" class="dropdown-item"
                    >모임</router-link
                  >
                </li>
                <li>
                  <router-link to="/" class="dropdown-item"
                    >회식</router-link
                  >
                </li>
              </ul>
            </li>
            <li class="nav-item dropdown">
              <router-link
                to=""
                class="nav-link dropdown-toggle"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                게시판
              </router-link>
              <ul class="dropdown-menu">
                <li>
                  <router-link to="/announce" class="dropdown-item"
                    >공지사항</router-link
                  >
                </li>
                <li>
                  <router-link to="/faq" class="dropdown-item"
                    >자주묻는질문</router-link
                  >
                </li>
                <li>
                  <router-link to="/qna" class="dropdown-item"
                    >질문과답변</router-link
                  >
                </li>
                <li>
                  <router-link to="/free" class="dropdown-item"
                    >자유게시판</router-link
                  >
                </li>
              </ul>
            </li>
          </ul>
        </div>
        <div
          class="collapse navbar-collapse border-lg-0 my-2 mt-lg-0"
          id="navbarSupportedContent"
        >
          <div class="mx-auto pt-5 pt-lg-0 d-block d-lg-none d-xl-block">
            <!-- 공간을 위한 안보이는 div -->
          </div>
          <form class="d-flex mt-4 mt-lg-0 ms-lg-auto ms-xl-0">
            <!-- 검색창 시작 -->
            <div class="input-group-icon pe-2">
              <i class="fas fa-search input-box-icon text-primary"></i>
              <input
                class="form-control border-0 input-box bg-100"
                type="search"
                placeholder="여기는 검색창"
                aria-label="Search"
              />
            </div>
            <!-- 검색창 끝 -->

            <div v-if="!currentUser">
              <!-- 회원가입 시작 -->
              <router-link
                to="/register"
                class="btn btn-white shadow-warning text-warning register"
              >
                <i class="fas fa-user me-2"></i>회원가입
              </router-link>
              <!-- 회원가입 끝 -->

              <!-- 로그인 시작 -->
              <router-link
                to="/login"
                class="btn btn-white shadow-warning text-warning login"
              >
                <i class="fas fa-user me-2"></i>로그인
              </router-link>
              <!-- 로그인 끝 -->
            </div>
            <div v-if="currentUser">
              <!-- 프로필 시작 -->
              <router-link
                to="/profile"
                class="btn btn-white shadow-warning text-warning profile"
              >
                <i class="fas fa-user me-2"></i>프로필
              </router-link>
              <!-- 프로필 끝 -->

              <!-- 로그아웃 시작 -->
              <a
                @click.prevent="logout"
                class="btn btn-white shadow-warning text-warning logout"
              >
                <i class="fas fa-user me-2"></i>
                로그아웃
              </a>
              <!-- 로그아웃 끝 -->
            </div>
          </form>
        </div>
      </div>
    </nav>
    <!-- 네비게이션 바 끝 -->
  </div>
</template>

<script>
export default {
  computed: {
    // 현재 유저
    currentUser() {
      // 모듈 저장소 : this.$store.state.모듈명.state값
      // user 객체 의 속성 : username, password, email, accesToken, roles(배열)
      return this.$store.state.auth.user;
    },
  },
  methods: {
    // 로그아웃 함수 -> 공통함수 호출
    logout() {
      // this.$store.dispatch("모듈명/함수명")
      this.$store.dispatch("auth/logout"); // 공통함수 logout 호출
      // alert 라이브러리 효과
      this.$swal({
        icon: "success",
        title: "로그아웃 성공",
        showConfirmButton: false,
        timer: 1000,
      });
      this.$router.push("/"); // 강제 홈페이지로 이동
    },
  },
};
</script>

<style>
.dropdown:hover .dropdown-menu {
  display: block;
  margin-top: 0;
}
</style>

