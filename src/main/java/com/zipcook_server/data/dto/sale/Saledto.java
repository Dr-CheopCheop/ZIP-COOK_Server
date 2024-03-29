package com.zipcook_server.data.dto.sale;

import com.zipcook_server.data.entity.SalePost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Saledto {

    private Long id;

    @Valid
    private String uid;

    @Size(max=30)
    private String title;



    private Date regDate;

    private String filepath;

    private String place;

    private String price;

    private String discountPrice;

    private String nickname;

    private String location;


    public Saledto(SalePost post){
        this.nickname= post.getNickname();
        this.id= post.getId();
        this.title= post.getTitle();
        this.regDate=post.getRegDate();
        this.place=post.getPlace();
        this.price=post.getPrice();
        this.discountPrice=post.getDiscountPrice();
        this.filepath=post.getFilepath();
        this.location=post.getLocation();
    }
}
