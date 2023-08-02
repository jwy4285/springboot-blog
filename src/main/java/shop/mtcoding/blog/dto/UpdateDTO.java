package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 글수정 API
 * 1. URL(엔드포인트까지) : http:localhost:8080/board/{id}/update
 * 2. method : POST GET은 바디없음
 * 3. 요청 body : title=값(String)&content=값(String)
 * 4. MINE타입 : x-www-form-urlencoded
 * 5. 응답 : view(html)를 응답함. detail 페이지
 * 이걸 프론트엔드 개발자한테 줌
 */
@Getter
@Setter
public class UpdateDTO {
    private String title;
    private String content;

}
