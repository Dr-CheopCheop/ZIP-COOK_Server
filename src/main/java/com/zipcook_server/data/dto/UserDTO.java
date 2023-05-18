package com.zipcook_server.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zipcook_server.data.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    private String email;

    @NotNull
    private String username; //아이디

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;

    @NotNull
    private String nickname; //이름

    @NotNull
    private String location;

    private Set<AuthorityDTO> authorityDtoSet;

    public static UserDTO from(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .location(user.getLocation())
                .nickname(user.getNickname())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDTO.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}