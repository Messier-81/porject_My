<template>
  <div>
    <!-- 홈페이지 로고 시작 -->
    <div class="container logo"><router-link to="/">임시로고(누르면 홈으로)</router-link></div>
    <!-- 홈페이지 로고 끝 -->
    <!-- 로그인 페이지 시작 -->
    <div class="col-md-12">
      <div class="form-structor container">
        <form name="form" @submit.prevent="handleLogin" class="signup">
          <h2 class="form-title" id="signup">로그인</h2>

          <div class="form-holder">
            <input
              v-model="user.username"
              v-validate="'required'"
              type="text"
              class="input"
              name="username"
              placeholder="아이디를 입력해주세요."
            />
            <input
              v-model="user.password"
              v-validate="'required'"
              type="password"
              class="input"
              name="password"
              placeholder="비밀번호를 입력해주세요"
            />
          </div>
          <button class="submit-btn">로그인</button>
          <div class="form-group">
            <div v-if="message" class="alert alert-danger" role="alert">
              {{ message }}
            </div>
          </div>
        </form>
        <div class="login slide-up">
          <div class="center">
            <h2 class="form-title" id="login">
              <router-link to="/register">회원가입</router-link>
            </h2>
          </div>
        </div>
      </div>
    </div>
    <!-- 로그인 페이지 끝 -->

    <!-- 푸터 마지막 한줄 -->
    <div class="row flex-center pb-3 loginfooter">
      <div class="col-md-6 order-0">
        <p class="text-center text-md-start">
          All rights Reserved &copy; Your Company, 2021
        </p>
      </div>
      <div class="col-md-6 order-1">
        <p class="text-center text-md-end">
          Made with&nbsp;
          <svg
            class="bi bi-suit-heart-fill"
            xmlns="http://www.w3.org/2000/svg"
            width="15"
            height="15"
            fill="#FFB30E"
            viewBox="0 0 16 16"
          >
            <path
              d="M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z"
            ></path></svg
          >&nbsp;by&nbsp;<router-link
            to=""
            class="fw-bold"
            href="https://themewagon.com/"
            target="_blank"
            >ThemeWagon
          </router-link>
        </p>
      </div>
    </div>
    <!-- 푸터 마지막 끝 -->

    <!-- 새 로그인 양식 임시 -->

    <!-- 새 로그인 양식 임시 끝-->
  </div>
</template>

<script>
/* eslint-disable */
import User from "@/model/user";

export default {
  data() {
    return {
      user: new User("", ""), // user 생성자 호출
      loading: false, // 로그인 버튼 활성화/비활성화 속성
      message: "",
    };
  },
  computed: {
    // loggedIn 상태 확인
    loggedIn() {
      // this.$store.state.모듈명.state값
      return this.$store.state.auth.status.loggedIn;
    },
  },
  // created() : Vue 생성되자 마자 실행되는 이벤트(화면은 생성되지 않은 상태)
  created() {
    if (this.loggedIn) {
      // 로그인이 되어 있는 상태
      this.$router.push("/profile"); // 강제 /profile 로 이동
    }
  },
  methods: {
    // 로그인 버튼 클릭시 실행되는 함수
    handleLogin() {
      //아이디나 비밀번호가 입력되지 않았다면 알림창을 띄우는 함수 실행
      this.alertLoginError();
      // 로그인 로직 처리
      this.loading = true;
      // vee-validate 함수 처리 방법
      this.$validator.validateAll().then((isValid) => {
        // validateAll() 모두 통과하면 -> isValid = true  (유효함)
        //                             -> isValid = false (유효하지 않음)
        if (!isValid) {
          this.loading = false;
          return; // 함수 탈출(break)
        }

        // isValid 가 true 이면 아래 로그인 실행(axios 실행)
        if (this.user.username && this.user.password) {
          // 공통함수 login 호출 :
          // this.$store.dispatch("모듈명/함수명", 매개변수)
          this.$store
            .dispatch("auth/login", this.user)
            .then(() => {
              // alert 라이브러리 효과
              this.$swal({
                icon: "success",
                title: "로그인 성공",
                showConfirmButton: false,
                timer: 1000,
              });
              this.$router.push("/profile"); // 로그인 성공하면 강제 /profile 페이지 이동
            })
            // 참고) if/else 문 대신에 -> or(||) and(&&) 연산자를 사용할때도 있음
            // 로직체크 순서 : true || false, false && true
            .catch((error) => {
              this.loading = false; // 로그인 버튼 활성화
              this.message =
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString();
            });
        }
      });
    },
    // 아이디나 비밀번호가 입력되지 않았다면 알림창을 띄우는 함수
    alertLoginError() {
      if (this.errors.has("username") && this.errors.has("password")) {
        // alert 라이브러리 효과
        this.$swal({
          icon: "error",
          title: "로그인 실패",
          text: "아이디와 비밀번호가 필요합니다",
          confirmButtonColor: "#E1793D",
          confirmButtonText: "확인",
        });
      } else if (this.errors.has("username")) {
        // alert 라이브러리 효과
        this.$swal({
          icon: "error",
          title: "로그인 실패",
          text: "아이디가 필요합니다",
          confirmButtonColor: "#E1793D",
          confirmButtonText: "확인",
        });
      } else if (this.errors.has("password")) {
        // alert 라이브러리 효과
        this.$swal({
          icon: "error",
          title: "로그인 실패",
          text: "비밀번호가 필요합니다",
          confirmButtonColor: "#E1793D",
          confirmButtonText: "확인",
        });
      }
    },
  },
};
</script>

<style scoped>
.logo {
  width: 350px;
  text-align: center;
  margin-top: 100px;
  background-color: aqua;
}

.loginfooter {
  position: fixed;
  bottom: 0;
  width: 100%;
}

/* 임시 로그인 css */
@import url("https://fonts.googleapis.com/css?family=Fira+Sans");
html,
body {
  position: relative;
  min-height: 100vh;
  background-color: #e1e8ee;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Fira Sans", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.form-structor {
  background-color: #222;
  border-radius: 15px;
  height: 550px;
  width: 350px;
  position: relative;
  overflow: hidden;
}
.form-structor::after {
  content: "";
  opacity: 0.8;
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background-repeat: no-repeat;
  background-position: left bottom;
  background-size: 500px;
  background-image: url("https://images.unsplash.com/photo-1503602642458-232111445657?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=bf884ad570b50659c5fa2dc2cfb20ecf&auto=format&fit=crop&w=1000&q=100");
}
.form-structor .signup {
  position: absolute;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  width: 65%;
  z-index: 5;
  -webkit-transition: all 0.3s ease;
}
.form-structor .signup.slide-up {
  top: 5%;
  -webkit-transform: translate(-50%, 0%);
  -webkit-transition: all 0.3s ease;
}
.form-structor .signup.slide-up .form-holder,
.form-structor .signup.slide-up .submit-btn {
  opacity: 0;
  visibility: hidden;
}
.form-structor .signup.slide-up .form-title {
  font-size: 1em;
  cursor: pointer;
}
.form-structor .signup.slide-up .form-title span {
  margin-right: 5px;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}
.form-structor .signup .form-title {
  color: #fff;
  font-size: 1.7em;
  text-align: center;
}
.form-structor .signup .form-title span {
  color: rgba(0, 0, 0, 0.4);
  opacity: 0;
  visibility: hidden;
  -webkit-transition: all 0.3s ease;
}
.form-structor .signup .form-holder {
  border-radius: 15px;
  background-color: #fff;
  overflow: hidden;
  margin-top: 50px;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}
.form-structor .signup .form-holder .input {
  border: 0;
  outline: none;
  box-shadow: none;
  display: block;
  height: 30px;
  line-height: 30px;
  padding: 8px 15px;
  border-bottom: 1px solid #eee;
  width: 100%;
  font-size: 12px;
}
.form-structor .signup .form-holder .input:last-child {
  border-bottom: 0;
}
.form-structor .signup .form-holder .input::-webkit-input-placeholder {
  color: rgba(0, 0, 0, 0.4);
}
.form-structor .signup .submit-btn {
  background-color: rgba(0, 0, 0, 0.4);
  color: rgba(255, 255, 255, 0.7);
  border: 0;
  border-radius: 15px;
  display: block;
  margin: 15px auto;
  padding: 15px 45px;
  width: 100%;
  font-size: 13px;
  font-weight: bold;
  cursor: pointer;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}
.form-structor .signup .submit-btn:hover {
  transition: all 0.3s ease;
  background-color: rgba(0, 0, 0, 0.8);
}
.form-structor .login {
  position: absolute;
  top: 20%;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #fff;
  z-index: 5;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login::before {
  content: "";
  position: absolute;
  left: 50%;
  top: -20px;
  -webkit-transform: translate(-50%, 0);
  background-color: #fff;
  width: 200%;
  height: 250px;
  border-radius: 50%;
  z-index: 4;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login .center {
  position: absolute;
  top: calc(50% - 10%);
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  width: 65%;
  z-index: 5;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login .center .form-title {
  color: #000;
  font-size: 1.7em;
  text-align: center;
}
.form-structor .login .center .form-title span {
  color: rgba(0, 0, 0, 0.4);
  opacity: 0;
  visibility: hidden;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login .center .form-holder {
  border-radius: 15px;
  background-color: #fff;
  border: 1px solid #eee;
  overflow: hidden;
  margin-top: 50px;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login .center .form-holder .input {
  border: 0;
  outline: none;
  box-shadow: none;
  display: block;
  height: 30px;
  line-height: 30px;
  padding: 8px 15px;
  border-bottom: 1px solid #eee;
  width: 100%;
  font-size: 12px;
}
.form-structor .login .center .form-holder .input:last-child {
  border-bottom: 0;
}
.form-structor .login .center .form-holder .input::-webkit-input-placeholder {
  color: rgba(0, 0, 0, 0.4);
}
.form-structor .login .center .submit-btn {
  background-color: #6b92a4;
  color: rgba(255, 255, 255, 0.7);
  border: 0;
  border-radius: 15px;
  display: block;
  margin: 15px auto;
  padding: 15px 45px;
  width: 100%;
  font-size: 13px;
  font-weight: bold;
  cursor: pointer;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login .center .submit-btn:hover {
  transition: all 0.3s ease;
  background-color: rgba(0, 0, 0, 0.8);
}
.form-structor .login.slide-up {
  top: 90%;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login.slide-up .center {
  top: 10%;
  -webkit-transform: translate(-50%, 0%);
  -webkit-transition: all 0.3s ease;
}
.form-structor .login.slide-up .form-holder,
.form-structor .login.slide-up .submit-btn {
  opacity: 0;
  visibility: hidden;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login.slide-up .form-title {
  font-size: 1em;
  margin: 0;
  padding: 0;
  cursor: pointer;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login.slide-up .form-title span {
  margin-right: 5px;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}
</style>
