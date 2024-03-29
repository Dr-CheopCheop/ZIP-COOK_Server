package com.zipcook_server.data.dto.share;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareCreate {

    @Valid
    private String uid;

    @NotBlank(message = "제목을 입력하세요")
    @Size(max=30)
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    @Size(max=80)
    private String content;

    private Date regDate;

    private String nickname;

    private String location;

}
