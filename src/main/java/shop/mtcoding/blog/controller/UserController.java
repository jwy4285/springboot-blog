package shop.mtcoding.blog.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.repository.UserRepositroy;

@Controller
public class UserController {

    // 비정상
    // @PostMapping("/join")
    // public String join(HttpServletRequest request) throws IOException {
    // //username=ssar&password=1234&email=ssar@nate.com
    // BufferedReader br = request.getReader(); //http바디데이터가들어옴
    // //버퍼가 소비됨
    // String body = br.readLine();

    // //버퍼에 값이 없어서 , 못꺼냄.
    // String username = request.getParameter("username"); //앞에서 버퍼가 소비가 되서 읽을수가 없음

    // System.out.println("username :" + username);
    // //밑에서 스플리트해야함
    // return "redirect:/loginForm";
    // }

    @Autowired
    private UserRepositroy userRepositroy;

    // DS(컨트롤러 메서드 찾기, 바디데이터 파싱)
    // DS가 바디데이터를 파싱안하고 컨트롤러 메서드만 찾은 상황
    // 클래스 각자 책임을 만들려고 만든것
    // 실무
    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {

        // validatuon check (유효성검사)
        if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }

        if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }

        if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
            return "redirect:/40x";
        }

        userRepositroy.save(joinDTO); // 핵심 기능
        return ("redirect:/loginForm");
    }

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

    // http:localhost:8080/user/updateForm
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
