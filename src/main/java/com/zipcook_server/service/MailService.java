package com.zipcook_server.service;

import com.zipcook_server.data.dto.UserDTO;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void sendCode(String username){
        Optional<User> user = userRepository.findOneWithAuthoritiesByUsername(username);
        Optional<UserDTO> userDTO = Optional.ofNullable(UserDTO.from(user.get()));
        String email = userDTO.get().getEmail();

        if(user.orElse(null) != null){
            String code = "";
            for (int i = 0; i < 12; i++) {
                code += (char) ((Math.random() * 26) + 97);
            }

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("ZIPCOOK 인증번호가 도착했습니다.");
            simpleMailMessage.setText(code);

        }
    }

    public UserDTO sendMail(String username){

        Optional<User> user1 = userRepository.findOneWithAuthoritiesByUsername(username);

        Optional<UserDTO> userDTO = Optional.ofNullable(UserDTO.from(user1.get()));
        String email = userDTO.get().getEmail();

        if(user1.isPresent()) {

            String pw = "";
            for (int i = 0; i < 12; i++) {
                pw += (char) ((Math.random() * 26) + 97);
            }

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("임시 비밀번호 발급 안내 이메일");
            simpleMailMessage.setText(pw);

            User user = User.builder()
                    .userId(user1.get().getUserId())
                    .username(user1.get().getUsername())
                    .email(email)
                    .location(user1.get().getLocation())
                    .nickname(user1.get().getNickname())
                    .password(passwordEncoder.encode(pw))
                    .authorities(user1.get().getAuthorities())
                    .build();

            javaMailSender.send(simpleMailMessage);

            Optional<UserDTO> dto = Optional.ofNullable(UserDTO.from(user));

            userRepository.save(user);

            return dto.get();
        }
        else {
            return userDTO.get();
        }
    }

}