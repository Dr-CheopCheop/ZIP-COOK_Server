package com.zipcook_server.data.dto.comment;

import com.zipcook_server.data.entity.Comment.SaleComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleCommentdto {

    private Long id;

    private Long board_id;

    private String username;

    private String nickname;

    private String content;

    private Date regDate;



    public SaleCommentdto(SaleComment comment){
        this.id= comment.getId();
        this.board_id=comment.getSalePost().getId();
        this.username=comment.getUsername();
        this.nickname=comment.getNickname();
        this.content=comment.getContent();
        this.regDate=comment.getRegDate();
    }
}
