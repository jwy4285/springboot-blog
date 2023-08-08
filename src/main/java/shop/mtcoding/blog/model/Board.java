package shop.mtcoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto가 create여야함
public class Board {
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 현재 데이터베이스 전략을 따라감
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = true, length = 10000)
    private String content;
    private Timestamp createdAt; // 생성일

    // private Integer userId; 이렇게 해도되는데 클래스 쓰는게 더 편함
    @ManyToOne
    private User user;

}
