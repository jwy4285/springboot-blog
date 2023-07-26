package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    // http://localhost:8080/
    // http://localhost:8080/board
    @GetMapping({ "/", "/board" })
    public String index() {
        return "index"; // view를 찾는다.
    }

    // http://localhost:8080/board/saveForm
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm"; // view를 찾는다.
    }

    // http://localhost:8080/board/1
    @GetMapping("/board/1")
    public String detail() {
        return "board/detail"; // view를 찾는다.
    }

}
