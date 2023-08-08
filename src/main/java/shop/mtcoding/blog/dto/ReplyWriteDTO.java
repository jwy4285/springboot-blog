package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyWriteDTO {
    private Integer boardId; // Integer로 해야 null체크가능해서 인트는 못함
    private String comment;
}
