package shop.mtcoding.blog.repository;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.bytebuddy.implementation.bytecode.ByteCodeAppender.Size;
import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.UpdateDTO;
import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;

@Repository
public class BoardRepository {
    @Autowired
    private EntityManager em;

    // select id, title from board_tb
    // resultClass 안붙이고 직접 파싱하려면
    // Object[] 로 리턴됨 ,
    // object[0] = 1
    // objcect[1] = 제목1
    public int count() {
        Query query = em.createNativeQuery("select count(*) from board_tb");
        // 원래는 Object 배열로 리턴 받는다, Object 배열은 칼럼의 연속이다.
        // 그룹함수를 써서, 하나의 칼럼을 조회하면, Object로 리턴된다.
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue();
    }

    public int count2() {
        // Entity(Board, User) 타입이 아니어도, 기본 자료형도 안되더라
        Query query = em.createNativeQuery("select * from board_tb", Board.class);
        return query.getMaxResults();
    }

    // localhost:8080?page=0
    public java.util.List<Board> findAll(int page) {
        final int SIZE = 3; // 상수는 대문자
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit :page, :size ", Board.class);
        query.setParameter("page", page * SIZE);
        query.setParameter("size", SIZE);
        return query.getResultList();
    }

    @Transactional
    public void save(WriteDTO writeDTO, Integer userId) {
        Query query = em.createNativeQuery(
                "insert into board_tb(title, content, user_id, created_at) values(:title, :content, :userId, now())");
        query.setParameter("title", writeDTO.getTitle());
        query.setParameter("content", writeDTO.getContent());
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    public Board findById(Integer id) {
        Query query = em.createNativeQuery("select * from board_tb where id = :id", Board.class);
        query.setParameter("id", id);
        Board board = (Board) query.getSingleResult();
        return board;
    }

    @Transactional
    public void deleteById(Integer id) {
        Query query = em.createNativeQuery("delete from board_tb where id = :id", Board.class);
        query.setParameter("id", id);
        query.executeUpdate();

    }

    @Transactional // 트랜잭션이 붙어 있으면 메서드가 끝날때 커밋을함
    public void update(UpdateDTO updateDTO, Integer id) {
        Query query = em.createNativeQuery("update board_tb set title = :title, content = :content where id = :id");
        query.setParameter("id", id);
        query.setParameter("title", updateDTO.getTitle());
        query.setParameter("content", updateDTO.getContent());

        query.executeUpdate();
    }

}
