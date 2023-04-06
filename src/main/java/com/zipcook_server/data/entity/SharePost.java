package com.zipcook_server.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UID")
    private User user;

    @Column(name = "title", length = 30)
    private String title;

    @Column(name = "location", length = 30)
    private String location;

    @Column(name = "content", length = 80)
    private String content;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_date")
    private Date regDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SharePost)) return false;
        SharePost sharePost = (SharePost) o;
        return Objects.equals(getId(), sharePost.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
