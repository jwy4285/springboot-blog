package shop.mtcoding.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

// User(1) - Reply(N)
// Borad(1) - Reply(N)
@Setter
@Getter
@Table(name = "reply_tb")
@Entity
public class Reply {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer Id;

    @Column(nullable = false, length = 100)
    private String comment; // 댓글내용

    @JoinColumn(name = "user_id") // 조인컬럼하면 직접 만들수있음
    @OneToOne
    private User user; // FK user_id 컬럼명 기본디폴트로 만들어짐

    @ManyToOne
    private Board board; // FK borad_id
}
