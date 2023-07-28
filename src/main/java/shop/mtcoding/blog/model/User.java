package shop.mtcoding.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "user_tb")
@Entity // ddl-auto가 create여야함
public class User {
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 현재 데이터베이스 전략을 따라감
    private Integer id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 20)

    private String password;

    @Column(nullable = false, length = 20)
    private String email;

}
