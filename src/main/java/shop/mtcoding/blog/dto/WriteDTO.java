package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 글쓰기 API
 * 1. URL(엔드포인트까지) : http:localhost:8080/save
 * 2. method : POST GET은 바디없음
 * 3. 요청 body : title=값(String)&content=값(String)
 * 4. MINE타입 : x-www-form-urlencoded
 * 5. 응답 : view(html)를 응답함. index 페이지
 * 이걸 프론트엔드 개발자한테 줌
 */
@Getter
@Setter
public class WriteDTO {
    private String title;
    private String content;

}
