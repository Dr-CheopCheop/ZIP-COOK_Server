package com.zipcook_server.controller;

import com.zipcook_server.data.dto.LoginDTO;
import com.zipcook_server.data.dto.TokenDTO;
import com.zipcook_server.jwt.JwtFilter;
import com.zipcook_server.jwt.TokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "로그인 API")
    public ResponseEntity<TokenDTO> authorize(@Valid @RequestBody LoginDTO loginDto, HttpServletResponse response) {

        //loginDTO 정보를 가지고 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        //authenticationToken을 이용해서 Authenticate 메서드를 실행하면 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //JWT 토큰 생성
        String jwt = tokenProvider.createToken(authentication);

        //헤더에 토큰 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAnyRole('USER')")
    @ApiOperation(value = "로그아웃", notes = "로그아웃 API")
    public void logout(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.remove(JwtFilter.AUTHORIZATION_HEADER);
    }
}

