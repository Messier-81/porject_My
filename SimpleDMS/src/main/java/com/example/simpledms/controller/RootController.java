package com.example.simpledms.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName : com.example.dongsungsi.controller
 * fileName : RootController
 * author : kangtaegyung
 * date : 2022/06/14
 * description : 새로고침 시 자동 Redirect 클래스( 배포버전에는 새로고침 시 Not Found 발생함 )
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/06/14         kangtaegyung          최초 생성
 */
@Controller
public class RootController implements ErrorController {

    @GetMapping("/error")
    public String redirectRoot() {
        return "index.html";
    }

}
