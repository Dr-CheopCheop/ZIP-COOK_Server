package com.zipcook_server.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UID")
    private User user;

    @Column(name = "title", length = 30)
    private String title;

    @Column(name = "serving")
    private int serving;

    @Column(name = "level", length = 5)
    private String level;

    @Column(name = "ingredients", length = 20)
    private String ingredients;

    @Column(name = "summary", length = 40)
    private String summary;

    @Column(name = "content", length = 80)
    private String content;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "time")
    private int time;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_date")
    private Date regDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipePost that = (RecipePost) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }


}