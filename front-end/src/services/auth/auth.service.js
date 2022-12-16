// auth.service.js : 로그인 함수/로그아웃/회원가입 함수
// TODO: 경로 변경
import http from "../../http-common";

class AuthService {
  // 로그인 함수
  // 1. front : DB에 사용자가 있는지 확인 요청( id/pwd )
  // 2. back  : DB에 사용자가 있는지 확인 (id)
  //         -> (if) 있으면 : 토큰발행해서 front로 전송 (user + 토큰)
  //         -> 없으면 : 에러메세지 전송
  login(user) {
    // user 매개변수를 임시 객체에 저장
    let userData = {
      username: user.username,
      password: user.password,
    };

    // axios 통신 : 백엔드에 로그인 요청 (db에 사용자가 있는지 조회 요청)
    //              로그인 인증 : Get 방식 x (보안) -> post 방식 사용
    return (
      http
        .post("/auth/signin", userData)
        // 요청 성공하면 then 실행
        .then((response) => {
          // 응답정보에서 토큰 있는지 체크
          if (response.data.accessToken) {
            // user정보 + 토큰 저장
            // localStorage : 텍스트 파일 ( 객체를 문자열로 변환해야함 )
            // JSON.stringify(객체) : 객체 -> 문자열로 변환됨
            localStorage.setItem("user", JSON.stringify(response.data));
          }

          return response.data;
        })
    );
  }

  // 로그아웃 함수
  logout() {
    localStorage.removeItem("user");
  }

  // 회원가입 함수
//   front : user 새회원정보를 db저장 요청
//   back : user 저장하기전에 -> password 암호화 -> DB 저장함
  register(user) {
    // user 매개변수를 임시 객체에 저장
    let userData = {
      username: user.username,
      email: user.email,
      password: user.password,
    };

    return http.post('/auth/signup', userData);
  }
}

export default new AuthService();
