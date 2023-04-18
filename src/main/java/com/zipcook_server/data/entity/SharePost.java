package com.zipcook_server.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", length = 500)
    private String content;

    @Temporal(TemporalType.DATE)
    @Column(name = "reg_date")
    private Date regDate;

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
