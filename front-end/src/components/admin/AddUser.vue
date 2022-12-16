<template>
  <div>
    <div id="hero">
      <div class="hero hero-inner bg-dark">
        <div
          id="board"
          class="container"
          data-aos="fade-up"
          data-aos-delay="100"
        >
          <div class="row align-items-center">
            <div class="col-lg-6 mx-auto text-center">
              <div class="intro-wrap">
                <h1>게시판 이름 넣는 곳</h1>
                <p class="text-white mb-5">쓰고 싶은 말을 쓰는 공간입니다</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-12">
      <div v-if="!submitted" class="container-fluid w-50">
        <form name="form" @submit.prevent="saveUser">
          <div v-if="!successful">
            <div class="form-group">
              <label for="username">Username</label>
              <!-- v-validate : 유효성 체크 -->
              <!-- v-validate="'required|min:3|max:20'" 체크함 -->
              <!-- errors.has('username') : 에러 발생했는지 검사 -->
              <!-- {{errors.first('username')}} : 에러 있으면 화면 출력됨, 없으면 안됨 -->
              <input
                v-model="user.username"
                v-validate="'required|min:3|max:20'"
                type="text"
                class="form-control"
                name="username"
              />
              <!-- 유효성 체크 후 에러 체크 : errors.hat(체크대상) -->
              <!-- errors.first(체크대상) : 에러 내용 출력 -->
              <div
                v-if="submitted && errors.has('username')"
                class="alert-danger"
              >
                {{ errors.first("username") }}
              </div>
            </div>
            <div class="form-group">
              <label for="email">Email</label>
              <input
                v-model="user.email"
                v-validate="'required|email|max:50'"
                type="email"
                class="form-control"
                name="email"
              />
              <div v-if="submitted && errors.has('email')" class="alert-danger">
                {{ errors.first("email") }}
              </div>
            </div>
            <div class="form-group">
              <label for="password">Password</label>
              <input
                v-model="user.password"
                v-validate="'required|min:6|max:40'"
                type="password"
                class="form-control"
                name="password"
              />
              <div
                v-if="submitted && errors.has('password')"
                class="alert-danger"
              >
                {{ errors.first("password") }}
              </div>
            </div>

            <div class="form-group">
              <label for="password">role</label>
              <select class="form-select" v-model="user.role">
                <option>ROLE_USER</option>
                <option>ROLE_ADMIN</option>
              </select>
            </div>

            <div class="form-group mt-3">
              <button class="btn btn-primary btn-block">Save</button>
            </div>
          </div>
        </form>
      </div>

      <div v-else>
        <h4>You submitted successfully!</h4>
        <button class="btn btn-success" @click="newUser">Add</button>
      </div>
    </div>
  </div>
</template>

<script>
import User from "@/model/user";
import UserDataService from "@/services/UserDataService";

export default {
  data() {
    return {
      user: new User("", "", "", "ROLE_USER"),
      submitted: false,
      successful: false,
      message: "",
    };
  },
  methods: {
    saveUser() {
      (this.message = ""), (this.submitted = true); // 저장 버튼 누를 때 true 변경
      // 유효성 체크
      this.$validator.validate().then((isValid) => {
        if (isValid) {
          UserDataService.create(this.user)
            .then((response) => {
              this.message = response.message;
            })
            .catch((e) => {
              console.log(e);
            });
        }
      });
    },
    newUser() {
      this.submitted = false;
      this.user = ("", "", "", "ROLE_USER");
    },
  },
};
</script>