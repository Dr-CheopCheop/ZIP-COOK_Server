package com.zipcook_server.service;

import com.zipcook_server.data.dto.share.ShareCreate;
import com.zipcook_server.data.dto.share.ShareEdit;
import com.zipcook_server.data.dto.share.ShareResponse;
import com.zipcook_server.data.entity.ShareEditor;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.data.request.ShareSearch;
import com.zipcook_server.repository.Share.ShareRepository;
import com.zipcook_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShareService {

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    UserRepository userRepository;

    public void write(ShareCreate shareCreate) throws IOException {
        User user = userRepository.findById(shareCreate.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        SharePost sharePost = SharePost.builder()
                .user(user)
                .title(shareCreate.getTitle())
                .content(shareCreate.getContent())
                .regDate(new Date())
                .build();

        shareRepository.save(sharePost);
    }

    public SharePost get(Long id){
        SharePost post=shareRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다"));

        return SharePost.builder()
                .id(post.getId())
                .user(post.getUser())
                .title(post.getTitle())
                .content(post.getContent())
                .regDate(post.getRegDate())
                .build();


    }

    public List<ShareResponse> getList(ShareSearch shareSearch){
        return shareRepository.getList(shareSearch).stream()
                .map(ShareResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, ShareEdit shareEdit){
        SharePost sharePost=shareRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다."));

        ShareEditor.ShareEditorBuilder shareEditorBuilder=sharePost.toEditor();

        ShareEditor shareEditor=shareEditorBuilder.title(shareEdit.getTitle())
                .content(shareEdit.getContent())
                .build();

        sharePost.edit(shareEditor);
    }

    public void delete(Long id){
        SharePost post=shareRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다"));

        shareRepository.delete(post);
    }


}
