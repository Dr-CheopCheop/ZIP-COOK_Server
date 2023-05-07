package com.zipcook_server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:monthFood.yml")
@Getter
@Setter
public class MonthFoodConfig {

    @Value("${january}")
    private String january;

    @Value("${february}")
    private String february;

    @Value("${march}")
    private String march;

    @Value("${april}")
    private String april;

    @Value("${may}")
    private String may;

    @Value("${june}")
    private String june;

    @Value("${july}")
    private String july;

    @Value("${august}")
    private String august;

    @Value("${september}")
    private String september;

    @Value("${october}")
    private String october;

    @Value("${november}")
    private String november;

    @Value("${december}")
    private String december;

}
