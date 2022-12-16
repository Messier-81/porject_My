export default function authHeader() {
  // auth-header.js : http 인증 헤더 파일 정의
  // 1. 토큰 가져오기
  // 2. { Authorization: 'Bearer ' + 토큰 } : 토큰 보내는 신호

  // 로컬스토리지 : user 키이름으로 정보 있음(with 토큰)
  let user = JSON.parse(localStorage.getItem("user"));

  // 헤더에 2번 정보(토큰 보내는 신호) 만들기
  if (user && user.accessToken) {
    // springboot 사용하는 헤더 토큰 인증 규칙
    return { Authorization: "Bearer " + user.accessToken };
    // return { 'x-access-token' : user.accessToken} // Node.js Express 서버
  } else {
    return {};
  }
}
