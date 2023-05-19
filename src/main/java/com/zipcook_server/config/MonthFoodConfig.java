package com.zipcook_server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "month-food")
@Getter
@Setter
public class MonthFoodConfig {

    private List<String> january;
    private List<String> february;
    private List<String> march;
    private List<String> april;
    private List<String> may;
    private List<String> june;
    private List<String> july;
    private List<String> august;
    private List<String> september;
    private List<String> october;
    private List<String> november;
    private List<String> december;

}
