package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 로그인 API
 * 1. URL(엔드포인트까지) : http:localhost:8080/join
 * 2. method : POST (로그인은 select 이지만, post로 한다)  GET은 바디없음
 * 3. 요청 body : username=값(String)&password=값(String)&email=값(String)
 * 4. MINE타입 : x-www-form-urlencoded
 * 5. 응답 : view(html)를 응답함. index 페이지
 * 이걸 프론트엔드 개발자한테 줌
 */
@Getter
@Setter
public class LoginDTO {
    private String username;
    private String password;

}
