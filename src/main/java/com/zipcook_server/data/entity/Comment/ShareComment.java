package com.zipcook_server.data.entity.Comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zipcook_server.data.dto.comment.ShareCommentdto;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.entity.User;
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
public class ShareComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String writer;

    private String content;


    @Temporal(TemporalType.DATE)
    @Column(name = "reg_date")
    private Date regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "UID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "sharepost_id")
    private SharePost sharePost;

    public void toUpdateEntity(ShareCommentdto shareCommentdto) {
        this.content = shareCommentdto.getContent();
        this.regDate = new Date();
    }

}
