package shop.mtcoding.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Reply;

// UserController, BoardController, ErrorController
// UserRepository, BoardRepository, ReplyRepository 내가 띄운거
// EntityManger, HttpSession 내가 띄운거아님
@Repository
public class ReplyRepository {

    @Autowired
    private EntityManager em;

    public Reply findById(int id) {
        Query query = em.createNativeQuery("select * from reply_tb where id = :id", Reply.class);
        query.setParameter("id", id);
        return (Reply) query.getSingleResult();
    }

    public List<Reply> findByBoardId(Integer boardId) {
        Query query = em.createNativeQuery(
                "select * from reply_tb where board_id", Reply.class);
        query.setParameter("boarId", boardId);
        return query.getResultList();
    }

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

    @Transactional
    public void deleteById(Integer id) {
        Query query = em.createNativeQuery("delete from reply_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
