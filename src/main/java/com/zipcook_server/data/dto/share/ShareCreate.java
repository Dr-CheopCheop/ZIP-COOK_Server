package com.zipcook_server.data.dto.share;

import com.zipcook_server.data.entity.User;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareCreate {

    @Valid
    private User user;

    @Size(max=30, message = "Title must be less than or equal to 30 characters.")
    private String title;

    @Size(max=80, message = "Content must be less than or equal to 80 characters.")
    private String content;

    private Date regDate;


}
