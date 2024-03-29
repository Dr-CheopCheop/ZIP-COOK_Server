package com.zipcook_server.service;

import com.zipcook_server.data.dto.share.ShareCreate;
import com.zipcook_server.data.dto.share.Sharedto;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.data.request.ShareMainSearch;
import com.zipcook_server.data.request.ShareSearch;
import com.zipcook_server.exception.PostNotFound;
import com.zipcook_server.repository.Share.ShareRepository;
import com.zipcook_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ShareService {


    private final ShareRepository shareRepository;


    private final UserRepository userRepository;


    private final ResourceLoader resourceLoader;

    public void write(ShareCreate shareCreate, MultipartFile file) throws IOException {


        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        String savepath="/Users/seunghee/Documents/boardimages/"+fileName;
        File saveFile = new File(savepath);
        file.transferTo(saveFile);

        SharePost sharePost = SharePost.builder()
                .nickname(shareCreate.getNickname())
                .title(shareCreate.getTitle())
                .content(shareCreate.getContent())
                .regDate(new Date())
                .filepath(fileName)
                .location(shareCreate.getLocation())
                .build();

        shareRepository.save(sharePost);
    }

    public Sharedto get(Long id,String location){
        SharePost post=shareRepository.findByIdAndLocation(id,location)
                .orElseThrow(PostNotFound::new);

        return Sharedto.builder()
                .id(post.getId())
                .nickname(post.getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .regDate(post.getRegDate())
                .filepath(post.getFilepath())
                .location(post.getLocation())
                .build();


    }


    public List<Sharedto> getList(ShareSearch shareSearch){
        return shareRepository.getList(shareSearch).stream()
                .map(Sharedto::new)
                .collect(Collectors.toList());
    }



    public List<Sharedto> getMain(){
        return shareRepository.getMainList().stream()
                .map(Sharedto::new)
                .collect(Collectors.toList());
    }




    public List<Sharedto> searchByTitle(String title,String location) {
        return shareRepository.findByTitleContainingAndLocation(title,location).stream()
                .map(Sharedto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id,String location,Sharedto update,MultipartFile file) throws IOException {
        SharePost sharePost=shareRepository.findByIdAndLocation(id,location)
                .orElseThrow(PostNotFound::new);

        File deleteFile = new File(sharePost.getFilepath());
        deleteFile.delete();

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        String savepath="/Users/seunghee/Documents/boardimages/"+fileName;
        File saveFile = new File(savepath);
        file.transferTo(saveFile);

        sharePost.toUpdateEntity(update,fileName);
        shareRepository.save(sharePost);
    }


    public void delete(Long id,String location){
        SharePost post=shareRepository.findByIdAndLocation(id,location)
                .orElseThrow(PostNotFound::new);

        File deleteFile = new File(post.getFilepath());
        deleteFile.delete();
        shareRepository.delete(post);
    }

}
