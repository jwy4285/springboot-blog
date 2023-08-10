package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.BoardDetailDTO;
import shop.mtcoding.blog.dto.UpdateDTO;
import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.BoardRepository;
import shop.mtcoding.blog.repository.ReplyRepository;

@Controller
public class BoardController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @ResponseBody
    @GetMapping("/test/reply")
    public List<Reply> test2() {
        List<Reply> replys = replyRepository.findByBoardId(1);
        return replys;
    }

    @ResponseBody
    @GetMapping("/test/board/1")

    public Board test() {
        Board board = boardRepository.findById(1);
        return board;
    }

    @PostMapping("/board/{id}/update") // 주소에 걸린건 웨얼에 걸림??
    public String update(@PathVariable Integer id, UpdateDTO updateDTO) { // DTO 약어 약어는 대문자로 포링키는 N
        // 1. 인증 검사

        // 2. 권한 체크

        // 3. 핵심 로직
        // update board_tb set title = :title, content = :content where id = :id
        boardRepository.update(updateDTO, id); // 웨얼절에 걸린 아이디도 넘겨줘야함

        return "redirect:/board/" + id; // 만들어져있는건 리다이렉트해야함

    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        // 1. 인증 검사

        // 2. 권환 체크

        // 3.핵심 로직
        Board board = boardRepository.findById(id);
        request.setAttribute("board", board);

        return "board/updateForm";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) { // 1. PathVariable 값 받기
        // 2.인증검사 (로그인 페이지 보내기)
        // session에 접근해서 sessionUser 키값을 가져오세요
        // null 이면, 로그인 페이지로 보내고
        // null 아니면 , 3번을 실행하세요
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; // 401

        }

        // 3. 권한검사
        Board board = boardRepository.findById(id);
        if (board.getUser().getId() != sessionUser.getId()) {
            return "redirect:40x"; // 403 권한없음

        }

        // 4. 모델에 접근해서 삭제
        // boardRepository.deleteById(id); 호출하세요 ->리턴을 받지 마세요
        // delete from board_tb where id = :id
        boardRepository.deleteById(id);
        System.out.println("테스트 1 ");
        return "redirect:/";
    }

    // http://localhost:8080/
    // http://localhost:8080/board
    // 게시글 목록보기
    @GetMapping({ "/", "/board" }) // 주소 두개를 지정할때 ({})
    // 가방에 담음 HttpServletRequest request
    public String index(
            String keyword1,
            @RequestParam(defaultValue = "0") Integer page,
            HttpServletRequest request) {
        // 1. 유효성 검사 x 바디데이터가 없으면 유효성검사 안해도됨(GET 요청 , 프로토콜)
        // 2. 인증검사 x 게시글 목록보기는 로그인안해도 누구나 볼 수 있게 해줄려고
        // 게시글 목록보기니까 DB에서 값을 조회해서 가져와야한다

        List<Board> boardList = null;
        int totalCount = 0;
        if (keyword1 == null) {
            boardList = boardRepository.findAll(page); // page = 1
            totalCount = boardRepository.count();
        } else {
            boardList = boardRepository.findAll(page, keyword1);
            totalCount = boardRepository.count(keyword1);
            request.setAttribute("keyword", keyword1);

        }

        // System.out.println("테스트 : totalcount :" + totalCount);
        int totalPage = totalCount / 3; // totalPage = 1
        if (totalCount % 3 > 0) {
            totalPage = totalPage + 1; // totalPage = 2

        }
        boolean last = totalPage - 1 == page;

        request.setAttribute("boardList", boardList);
        request.setAttribute("prevPage", page - 1);
        request.setAttribute("nextPage", page + 1);
        request.setAttribute("first", page == 0 ? true : false);
        request.setAttribute("last", last);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalCount", totalCount);

        return "index"; // view를 찾는다.
    }

    @PostMapping("/board/save")
    public String save(WriteDTO writeDTO) {
        // validation check (유효성 검사)
        if (writeDTO.getTitle() == null || writeDTO.getTitle().isEmpty()) {
            System.out.println("테스트4");
            return "redirect:/40x";

        }

        if (writeDTO.getContent() == null || writeDTO.getContent().isEmpty()) {
            System.out.println("테스트3");
            return "redirect:/40x";
        }
        // 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            System.out.println("테스트2");
            return "redirect:/loginForm";

        }

        boardRepository.save(writeDTO, sessionUser.getId());
        System.out.println("테스트1");
        return "redirect:/";

    }

    // http://localhost:8080/board/saveForm
    @GetMapping("/board/saveForm")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";

        } // 인증이 필요한 페이지는 이 코드 필요
        return "board/saveForm"; // view를 찾는다.
    }

    // localhost:8080/board/1
    // localhost:8080/board/50
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) { // C
        User sessionUser = (User) session.getAttribute("sessionUser"); // 세션접근
        List<BoardDetailDTO> dtos = null;
        if (sessionUser == null) {
            dtos = boardRepository.findByIdJoinReply(id, null);
        } else {
            dtos = boardRepository.findByIdJoinReply(id, sessionUser.getId());
        }

        boolean pageOwner = false;
        if (sessionUser != null) {
            pageOwner = sessionUser.getId() == dtos.get(0).getBoardUserId();
        }

        request.setAttribute("dtos", dtos);
        request.setAttribute("pageOwner", pageOwner);
        return "board/detail"; // V
    }
}