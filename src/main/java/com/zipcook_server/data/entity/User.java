package com.zipcook_server.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "tbl_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id") //자동증가 primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "location")
    private String location;

    @ManyToMany
    @JoinTable(
            name = "user_authority", //연결테이블 이름
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")}, //
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})

    private Set<Authority> authorities;

}