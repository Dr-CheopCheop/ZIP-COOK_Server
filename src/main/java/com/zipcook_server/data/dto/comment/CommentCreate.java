package com.zipcook_server.data.dto.comment;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreate {


    private Long board_id;

    @Size(max=30)
    private String nickname;

    private String content;

    private Date regDate;

}
