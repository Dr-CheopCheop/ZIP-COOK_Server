package com.zipcook_server.service;

import com.zipcook_server.data.dto.UserDTO;
import com.zipcook_server.data.entity.Authority;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.repository.UserRepository;
import com.zipcook_server.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입
    @Transactional
    public UserDTO signup(UserDTO userDto){
        //유저면 권한 정보 생성
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        //권한 정보를 유저 객체에 저장
        User user = User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .location(userDto.getLocation())
                .authorities(Collections.singleton(authority))
                .build();

        //새로 가입한 회원 정보 DB에 저장
        return UserDTO.from(userRepository.save(user));

    }

    //아이디 중복체크
    @Transactional(readOnly = true)
    public Boolean checkUsernameDuplicate(String username){
        if(userRepository.existsByUsername(username)) return true;
        return false;
    }

    //이메일 중복체크
    @Transactional(readOnly = true)
    public Boolean checkEmailDuplicate(String email) {
        if(userRepository.existsByEmail(email)) return true;
        return false;
    }

    //닉네임 중복체크
    @Transactional(readOnly = true)
    public Boolean checkNicknameDuplicate(String nickname) {
        if(userRepository.existsByNickname(nickname)) return true;
        return false;
    }

    //username으로 권한 정보 가져오기
    @Transactional(readOnly = true)
    public UserDTO getUserWithAuthorities(String username) {
        return UserDTO.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public String getUserNickname(String username){
        return UserDTO.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null)).getNickname();
    }

    //현재 SecurityContext 에 저장된 username의 정보 가져오기
    @Transactional(readOnly = true)
    public UserDTO getMyUserWithAuthorities() {
        return UserDTO.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
    }


    @Transactional(readOnly = true)
    public Optional<String> verifyPassword(UserDTO userDTO) {

        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        String msg = null;
        if (userRepository.findOneWithAuthoritiesByUsername(username).orElse(null) == null) {
            return Optional.of(msg);
        }

        Optional<User> user = userRepository.findByUsername(username);

        if (passwordEncoder.matches(password, user.get().getPassword())) {
            return Optional.ofNullable("Verified");
        }
        else{
            return Optional.of(msg);
        }
    }

    @Transactional
    public Optional<String> updatePassword(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        Optional<User> temp = userRepository.findByUsername(username);

        if (userRepository.findOneWithAuthoritiesByUsername(username).orElse(null) != null) {

            User user = User.builder()
                    .userId(temp.get().getUserId())
                    .email(temp.get().getEmail())
                    .nickname(temp.get().getNickname())
                    .location(temp.get().getLocation())
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .authorities(temp.get().getAuthorities())
                    .build();
            userRepository.save(user);

            return Optional.of("패스워드를 변경하였습니다.");
        }
        else{
            return Optional.of(null);
        }
    }



}