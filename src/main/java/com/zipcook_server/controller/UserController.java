package com.zipcook_server.controller;

import com.zipcook_server.data.dto.UserDTO;
import com.zipcook_server.service.MailService;
import com.zipcook_server.service.UserService;
import com.zipcook_server.util.SecurityUtil;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody UserDTO userDto){
        userService.signup(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //아이디 중복체크
    @GetMapping("/exist/username/{username}")
    public ResponseEntity<Map<String, Object>> checkUsernameDuplicate(@PathVariable String username){
        boolean isDuplicate = userService.checkUsernameDuplicate(username);

        Map<String, Object> response = new HashMap<>();
        response.put("duplicate", isDuplicate);

        HttpStatus status = isDuplicate ? HttpStatus.CONFLICT : HttpStatus.OK;

        return ResponseEntity.status(status).body(response);
    }

    //닉네임 중복체크
    @GetMapping("/exist/nickname/{nickname}")
    public ResponseEntity<Map<String, Object>> checkNicknameDuplicate(@PathVariable String nickname) {
        boolean isDuplicate = userService.checkNicknameDuplicate(nickname);

        Map<String, Object> response = new HashMap<>();
        response.put("duplicate", isDuplicate);

        HttpStatus status = isDuplicate ? HttpStatus.CONFLICT : HttpStatus.OK;

        return ResponseEntity.status(status).body(response);
    }

    //이메일 중복체크
    @GetMapping("/exist/email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmailDuplicate(@PathVariable String email) {
        boolean isDuplicate = userService.checkEmailDuplicate(email);

        Map<String, Object> response = new HashMap<>();
        response.put("duplicate", isDuplicate);

        HttpStatus status = isDuplicate ? HttpStatus.CONFLICT : HttpStatus.OK;

        return ResponseEntity.status(status).body(response);
    }

    //유저정보 by jwt token
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDTO> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }


    //위와 같은 반환 값
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }

    //아이디 반환
    @GetMapping("/getUsername")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Optional<String> getUsername() {
        Optional<String> username = SecurityUtil.getCurrentUsername();

        return username;
    }

    //비밀번호 변경 이메일 전송
    @GetMapping("/findPassword/{username}")
    public ResponseEntity<Void> sendMail(@PathVariable String username) {
        mailService.sendMail(username);
        return ResponseEntity.ok().build();
    }

    //변경된 비밀번호 확인
    @GetMapping("/findPassword/verifyCode")
    public ResponseEntity<String> verifyPassword(@RequestBody UserDTO userDTO) {
        Optional<String> result = userService.verifyPassword(userDTO);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //비밀번호 변경
    @PostMapping("/findPassword/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UserDTO userDTO) {
        Optional<String> result = userService.updatePassword(userDTO);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

}