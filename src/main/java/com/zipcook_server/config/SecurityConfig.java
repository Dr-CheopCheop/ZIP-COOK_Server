package com.zipcook_server.config;

import com.zipcook_server.jwt.JwtAccessDeniedHandler;
import com.zipcook_server.jwt.JwtAuthenticationEntryPoint;
import com.zipcook_server.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CorsFilter corsFilter;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler,
            CorsFilter corsFilter
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.corsFilter = corsFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/h2-console/**"
                        , "/favicon.ico"
                        , "/error"
                );
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이므로  csrf를 disable
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll() //로그인
                .antMatchers("/auth/signup").permitAll() //회원가입
                .antMatchers("/auth/findPassword/**").permitAll() //비밀번호 찾기
                .antMatchers("/auth/findId/**").permitAll() //아이디 찾기
                .antMatchers("/auth/exist/**").permitAll() //중복체크
                .antMatchers("/chatbot/**").permitAll()
                .antMatchers("/v2/api-docs", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**").permitAll()
                .antMatchers("/recipe-comment/**").permitAll()
                .antMatchers("/sale-comment/**").permitAll()
                .antMatchers("/share-comment/**").permitAll()
                .antMatchers("/main/**").permitAll()
                .antMatchers("/board-sale/**").permitAll()
                .antMatchers("/board-share/**").permitAll()
                .antMatchers("/board-recipe/**").permitAll()



                .anyRequest().authenticated()

                .and()
                .logout().logoutUrl("/logout").permitAll()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }
}