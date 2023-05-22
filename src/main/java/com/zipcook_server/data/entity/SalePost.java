package com.zipcook_server.data.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zipcook_server.data.dto.sale.Saledto;
import com.zipcook_server.data.entity.Comment.SaleComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "UID")
    private User user;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", length = 500)
    private String content;

    @Temporal(TemporalType.DATE)
    @Column(name = "reg_date")
    private Date regDate;

    private String nickname;

    private String filepath;
    @Column(name = "place")
    private String place;
    @Column(name = "price")
    private String price;
    @Column(name = "discountPrice")
    private String discountPrice;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "salePost", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    @Builder.Default
    private List<SaleComment> saleComments = new ArrayList<>();

    public void toUpdateEntity(Saledto saleUpdate, String filepath) {
        this.title = saleUpdate.getTitle();
        this.content = saleUpdate.getContent();
        this.regDate = new Date();
        this.place= saleUpdate.getPlace();
        this.price=saleUpdate.getPrice();
        this.discountPrice= saleUpdate.getDiscountPrice();
        this.filepath = filepath;
        this.location= saleUpdate.getLocation();
    }
}
