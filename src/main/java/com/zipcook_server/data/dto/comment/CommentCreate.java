package com.zipcook_server.data.dto.comment;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreate {

    private String username;

    private String nickname;


    private Long board_id;

    private String content;

    private Date regDate;

}
