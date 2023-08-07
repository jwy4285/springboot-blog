package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.model.User;

// BoardController, UserController, UserRepository <내가 직접 띄운거
// EntityManger, HttpSession <스프링이 직접 띄운거
@Repository // 알아서 new해줌
public class UserRepository {
    @Autowired
    // EntityManager : JAP를 사용하여 데이터 베이스와 상호작용하기 위한 객체
    private EntityManager em; // 디비커넥션 데이터베이스 연결 및 쿼리 실행과 같은 작업을 수행하는 역할

    public User findByUsername(String username) {
        try {
            Query query = em.createNativeQuery("select * from user_tb where username=:username",
                    User.class);
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    public User findByUsernameAndPassword(LoginDTO loginDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username=:username and password=:password",
                User.class);
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("password", loginDTO.getPassword());
        return (User) query.getSingleResult();
    }

    @Transactional
    public void save(JoinDTO joinDTO) {
        System.out.println("테스트 :" + 1);
        Query query = em.createNativeQuery(
                "insert into user_tb(username, password, email) values(:username, :password, :email)");
        System.out.println("테스트 :" + 2);
        query.setParameter("username", joinDTO.getUsername());
        query.setParameter("password", joinDTO.getPassword());
        query.setParameter("email", joinDTO.getEmail());
        System.out.println("테스트 :" + 3);
        query.executeUpdate(); // 쿼리를 전송하는 코드 (DBMS) 이 전까지는 쿼리를 만드는것 DB는 그냥 하드디스크
        System.out.println("테스트 :" + 4);
    }

}
