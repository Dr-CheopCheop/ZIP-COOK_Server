package com.zipcook_server.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    @JsonIgnore
    @JoinColumn(name = "UID")
    private User user;

    @Column(name = "title", length = 30)
    private String title;


    @Column(name = "content", length = 80)
    private String content;

    @Temporal(TemporalType.DATE)
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

    public  ShareEditor.ShareEditorBuilder toEditor(){
        return ShareEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(ShareEditor shareEditor){
        title=shareEditor.getTitle();
        content=shareEditor.getContent();
    }

}
