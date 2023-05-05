package com.zipcook_server.service;

import com.zipcook_server.data.dto.share.ShareCreate;
import com.zipcook_server.data.dto.share.ShareEdit;
import com.zipcook_server.data.dto.share.ShareResponse;
import com.zipcook_server.data.entity.ShareEditor;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.data.request.ShareSearch;
import com.zipcook_server.exception.PostNotFound;
import com.zipcook_server.repository.Share.ShareRepository;
import com.zipcook_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShareService {

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    public void write(ShareCreate shareCreate, MultipartFile file) throws IOException {
        User user = userRepository.findById(shareCreate.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        String savepath="/Users/seunghee/Documents/boardimages/"+fileName;
        File saveFile = new File(savepath);
        file.transferTo(saveFile);

        SharePost sharePost = SharePost.builder()
                .user(user)
                .title(shareCreate.getTitle())
                .content(shareCreate.getContent())
                .regDate(new Date())
                .filepath(savepath)
                .build();

        shareRepository.save(sharePost);
    }

    public ShareResponse get(Long id){
        SharePost post=shareRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return ShareResponse.builder()
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



    public List<ShareResponse> searchEntities(String keyword) {
        return shareRepository.findByTitleContaining(keyword).stream()
                .map(ShareResponse::new)
                .collect(Collectors.toList());
    }

    public List<ShareResponse> searchByTitle(String title) {
        return shareRepository.findByTitleContaining(title).stream()
                .map(ShareResponse::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void edit(Long id, ShareEdit shareEdit) throws IOException {
        SharePost sharePost=shareRepository.findById(id)
                .orElseThrow(PostNotFound::new);


        ShareEditor.ShareEditorBuilder shareEditorBuilder=sharePost.toEditor();

        ShareEditor shareEditor=shareEditorBuilder.title(shareEdit.getTitle())
                .content(shareEdit.getContent())
                .build();

        sharePost.edit(shareEditor);
    }

    public void delete(Long id){
        SharePost post=shareRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        File deleteFile = new File(post.getFilepath());
        deleteFile.delete();
        shareRepository.delete(post);
    }


}
