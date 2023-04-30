package com.zipcook_server.data.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaleEditor {

    private String title;

    private String content;
}
