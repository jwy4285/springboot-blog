package shop.mtcoding.blog.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.UserRepository;

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
    private UserRepository userRepositroy;

    @Autowired
    private HttpSession session; // request는 가방 , session은 서랍 (다음번에 올때도 있음)

    @ResponseBody
    @GetMapping("/test/login")
    public String testLogin() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "로그인이 되지 않았습니다";
        } else {
            return "로그인 됨 : " + sessionUser.getUsername();
        }
    }

    @PostMapping("/login")
    public String login(LoginDTO loginDTO) {
        if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }

        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }

        // 핵심 기능
        try {
            User user = userRepositroy.findByUsernameAndPassword(loginDTO);
            session.setAttribute("sessionUser", user);

            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/exLogin";
        }
    }

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
        try {
            userRepositroy.save(joinDTO); // 핵심 기능
        } catch (Exception e) {
            return "redirect:/50x";
        }

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
    // 세션에 정보가 있어서 굳이 request를 쓸 필요가 없음
    public String updateForm() {
        // 1. 세션 정보를 가져와
        User sessionUser = (User) session.getAttribute("sessionUser");

        return "user/updateForm"; // view를 찾는다.
    }

    // http:localhost:8080
    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // 세션 전체를 비워버리는것 세션 무효화(내 서랍을 비우는 것)
        return "redirect:/"; // 컨트롤러 주소를 찾는다.
    }

}
