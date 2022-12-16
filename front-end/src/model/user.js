// user.js 공통 파일: 로그인 유저정보 저장
// 유저객체 생성 : let user = new User(매개변수...);
// TODO: user 객체 role 매개변수 추가
export default class User {
    // js 생성자 : constructor(매개변수...) {}
    constructor(username, email, password, role) {
        this.username = username; // 사용자 id
        this.email = email; // 사용자 email
        this.password = password; // 사용자 비밀번호
        this.role = role; // 사용자 권한
    }
}