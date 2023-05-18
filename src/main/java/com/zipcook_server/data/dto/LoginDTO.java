package com.zipcook_server.data.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotNull
    @ApiModelProperty(example = "아이디")
    private String username;

    @NotNull
    @ApiModelProperty(example = "비밀번호")
    private String password;
}
