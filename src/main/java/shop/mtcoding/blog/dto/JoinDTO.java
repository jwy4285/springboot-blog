package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 회원가입 API
 * 1. URL(엔드포인트까지) : http:localhost:8080/join
 * 2. method : POST   GET은 바디없음
 * 3. 요청 body : username=값(String)&password=값(String)&email=값(String)
 * 4. MINE타입 : x-www-form-urlencoded
 * 5. 응답 : view를 응답함.
 * 이걸 프론트엔드 개발자한테 줌
 */

@Setter
@Getter
public class JoinDTO {
    private String username;
    private String password;
    private String email;

}
