<template>
  <div>
    <link
      href="https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css"
      rel="stylesheet"
    />

    <div class="login">
      <div class="login__content">
        <div class="login__img">
          <img
            src="assets/img/gallery/hero-tomato.png"
            alt="user login"
          />
        </div>
        <div class="login__forms">
          <!--         create account form -->
          <form
            action=""
            class="login__create"
            id="login-up"
            name="form"
            @submit.prevent="handleRegister"
          >
            <div v-if="!successful">
              <h1 class="login__title">회원가입</h1>
              <div>
                <div class="login__box">
                  <i class="bx bx-user login__icon"></i>
                  <input
                    v-model="user.username"
                    v-validate="'required|min:3|max:20'"
                    type="text"
                    placeholder="아이디"
                    class="login__input"
                    name="username"
                  />
                </div>
                <div
                  v-if="submitted && errors.has('username')"
                  class="alert-danger"
                >
                  {{ errors.first("username") }}
                </div>
              </div>

              <div>
                <div class="login__box">
                  <i class="bx bx-at login__icon"></i>
                  <input
                    v-model="user.email"
                    v-validate="'required|email|max:50'"
                    type="email"
                    placeholder="이메일"
                    class="login__input"
                    name="email"
                  />
                </div>
                <div
                  v-if="submitted && errors.has('email')"
                  class="alert-danger"
                >
                  {{ errors.first("email") }}
                </div>
              </div>

              <div>
                <div class="login__box">
                  <i class="bx bx-lock login__icon"></i>
                  <input
                    v-model="user.password"
                    v-validate="'required|min:6|max:40'"
                    type="password"
                    placeholder="비밀번호"
                    class="login__input"
                    name="password"
                  />
                </div>
                <div
                  v-if="submitted && errors.has('password')"
                  class="alert-danger"
                >
                  {{ errors.first("password") }}
                </div>
              </div>

              <button class="login__button">가입하기</button>

              <div
                v-if="message"
                class="alert"
                :class="successful ? 'alert-success' : 'alert-danger'"
              >
                {{ message }}
              </div>
              <div>
                <span class="login__account login__account--account"
                  >이미 회원이신가요?</span
                >
                &nbsp;
                <router-link to="/login" class="login__signup login__signup--signup" id="sign-in"
                  >로그인 하기</router-link
                >
              </div>

              <div class="login__social">
                <a href="#" class="login__social--icon"
                  ><i class="bx bxl-facebook"></i
                ></a>
                <a href="#" class="login__social--icon"
                  ><i class="bx bxl-twitter"></i
                ></a>
                <a href="#" class="login__social--icon"
                  ><i class="bx bxl-google"></i
                ></a>
                <a href="#" class="login__social--icon"
                  ><i class="bx bxl-github"></i
                ></a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import User from "@/model/user";

export default {
  data() {
    return {
      // 기본적으로 ROLE_USER로 받고 ROLE 정보를 화면에서 수정할 수 있는 기능을 추가
      // TODO: user 객체 role 매개변수 추가
      user: new User("", "", "", "ROLE_USER"),
      submitted: false,
      successful: false,
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
    // 회원가입 버튼 클릭시 실행되는 함수
    handleRegister() {
      this.message = "";
      this.submitted = true; //  회원가입 버튼 클릭 = true
      // vee-validate 함수 처리 방법
      this.$validator.validateAll().then((isValid) => {
        // validateAll() 모두 통과하면 -> isValid = true  (유효함)
        //                             -> isValid = false (유효하지 않음)
        if (!isValid) {
          return; // 함수 탈출(break)
        }

        // 공통함수 register 호출 :
        // this.$store.dispatch("모듈명/함수명", 매개변수)
        this.$store
          .dispatch("auth/register", this.user)
          .then((response) => {
            this.message = response.message;
            this.successful = true; // "회원가입이 성공했습니다." 화면 출력
          })
          // 참고) if/else 문 대신에 -> or(||) and(&&) 연산자를 사용할때도 있음
          // 로직체크 순서 : true || false, false && true
          .catch((error) => {
            this.successful = false; // 회원가입 실패 -> 회원가입폼 다시 화면에 나옴
            this.message =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();
          });
      });
    },
  },
  mounted() {
    const signup = document.getElementById("sign-up");
    const signin = document.getElementById("sign-in");
    const loginin = document.getElementById("login-in");
    const loginup = document.getElementById("login-up");

    signup.addEventListener("click", () => {
      loginin.classList.remove("block");
      loginup.classList.remove("none");

      loginin.classList.add("none");
      loginup.classList.add("block");
    });

    signin.addEventListener("click", () => {
      loginin.classList.remove("none");
      loginup.classList.remove("block");

      loginin.classList.add("block");
      loginup.classList.add("none");
    });
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600;700&display=swap");
*,
::before,
::after {
  box-sizing: border-box;
}

body {
  margin: 0;
  padding: 0;
  font-family: "Open Sans", sans-serif;
  font-size: 0.938rem;
  color: #23004d;
}

h1 {
  margin: 0;
}

a {
  text-decoration: none;
}

img {
  max-width: 100%;
  max-height: 100%;
  height: auto;
  display: block;
}

.login {
  display: grid;
  grid-template-columns: 100%;
  height: 100vh;
  margin-left: 1.5rem;
  margin-right: 1.5rem;
}
.login__content {
  display: grid;
}
.login__img {
  justify-self: center;
}
.login__img img {
  width: 310px;
  margin-top: 1.5rem;
}
.login__forms {
  position: relative;
  height: 368px;
}
.login__register,
.login__create {
  position: absolute;
  bottom: 1rem;
  width: 100%;
  background-color: #f2f2f2;
  padding: 2rem 1rem;
  border-radius: 1rem;
  text-align: center;
  box-shadow: 0 8px 20px rgba(35, 0, 77, 0.2);
  animation-duration: 0.4s;
  animation-name: animateLogin;
}
.login__title {
  font-size: 1.5rem;
  margin-bottom: 2rem;
}
.login__box {
  display: grid;
  grid-template-columns: max-content 1fr;
  column-gap: 0.5rem;
  padding: 1.125rem 1rem;
  background-color: #fff;
  margin-top: 1rem;
  border-radius: 0.5rem;
}
.login__icon {
  font-size: 1.5rem;
  color: #FFB30E;
}
.login__input {
  border: none;
  outline: none;
  font-size: 0.938rem;
  font-weight: 700;
  color: #23004d;
  width: 100%;
}
.login__input::placeholder {
  font-size: 0.938rem;
  font-family: "Open Sans", sans-serif;
  color: #a49eac;
}
.login__forgot {
  display: block;
  width: max-content;
  margin-left: auto;
  margin-top: 0.5rem;
  font-size: 0.813rem;
  font-weight: 600;
  color: #a49eac;
}
.login__button {
  width: 316px;
  padding: 1rem;
  margin: 2rem 0;
  background-color: #FFB30E;
  color: #fff;
  font-weight: 600;
  text-align: center;
  border-radius: 0.5rem;
  transition: 0.3s;
  border: 0;
}
.login__button:hover {
  background-color: #ffb30ea8;
}
.login__account,
.login__signin,
.login__signup {
  font-weight: 600;
  font-size: 0.813rem;
}
.login__account--account,
.login__signin--account,
.login__signup--account {
  color: #23004d;
}
.login__account--signin,
.login__account--signup,
.login__signin--signin,
.login__signin--signup,
.login__signup--signin,
.login__signup--signup {
  color: #FFB30E;
  cursor: pointer;
}
.login__social {
  margin-top: 2rem;
}
.login__social--icon {
  font-size: 1.5rem;
  color: #23004d;
  margin: 0 1rem;
}

.block {
  display: block;
}

.none {
  display: none;
}

@keyframes animateLogin {
  0% {
    transform: scale(1, 1);
  }
  50% {
    transform: scale(1.1, 1.1);
  }
  100% {
    transform: scale(1, 1);
  }
}
@media screen and (min-width: 576px) {
  .login__forms {
    width: 348px;
    justify-self: center;
  }
}
@media screen and (min-width: 1024px) {
  .login {
    height: 100vh;
    overflow: hidden;
  }
  .login__content {
    grid-template-columns: repeat(2, max-content);
    justify-content: center;
    align-items: center;
    margin-left: 10rem;
  }
  .login__img {
    display: flex;
    width: 600px;
    height: 588px;
    background-color: #fff;
    border-radius: 1rem;
    padding-left: 1rem;
  }
  .login__img img {
    width: 100%;
    margin-top: 0;
  }
  .login__register,
  .login__create {
    left: -11rem;
  }
  .login__register {
    bottom: -2rem;
  }
  .login__create {
    bottom: -5.5rem;
  }
}
</style>
