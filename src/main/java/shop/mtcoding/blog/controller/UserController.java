package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    // ip 주소 부여 : 10.5.9.200 -> mtcoding.com:8080
    // localhost, 127.0.0.1
    // get요청하는방법 : a태그 , form태그 method=get 하이퍼링크:추가 정보를 달라는것
    // http:localhost:8080/joinForm
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm"; // resources/templates/리턴값.mustache
    }

    // http:localhost:8080/loginForm
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm"; // view를 찾는다.
    }

    // http:localhost:8080/updateForm
    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm"; // view를 찾는다.
    }

    // http:localhost:8080
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/"; // 컨트롤러 주소를 찾는다.
    }

}
