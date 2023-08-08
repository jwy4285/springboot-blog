package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.ReplyWriteDTO;

// UserController, BoardController, ErrorController
// UserRepository, BoardRepository, ReplyRepository 내가 띄운거
// EntityManger, HttpSession 내가 띄운거아님
@Repository
public class ReplyRepository {

    @Autowired
    private EntityManager em;

    @Transactional
    public void save(ReplyWriteDTO replyWriteDTO, Integer userId) {
        Query query = em.createNativeQuery(
                "insert into reply_tb(comment ,board_id, user_id) values(:comment, :boardId, :userId)");
        // 쿼리완성
        query.setParameter("comment", replyWriteDTO.getComment());
        query.setParameter("boardId", replyWriteDTO.getBoardId());
        query.setParameter("userId", userId);
        // 쿼리전송
        query.executeUpdate();

    }
}
