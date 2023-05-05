package com.zipcook_server.service;

import com.zipcook_server.data.dto.sale.SaleCreate;
import com.zipcook_server.data.dto.sale.Saledto;
import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.data.request.SaleSearch;
import com.zipcook_server.exception.PostNotFound;
import com.zipcook_server.repository.Sale.SaleRepository;
import com.zipcook_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SaleService {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    UserRepository userRepository;

    public void write(SaleCreate saleCreate, MultipartFile file) throws IOException {
        User user = userRepository.findById(saleCreate.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        String savepath="/Users/seunghee/Documents/boardimages/sale/"+fileName;
        File saveFile = new File(savepath);
        file.transferTo(saveFile);

        SalePost salePost = SalePost.builder()
                .user(user)
                .title(saleCreate.getTitle())
                .content(saleCreate.getContent())
                .regDate(new Date())
                .filepath(savepath)
                .build();

        saleRepository.save(salePost);
    }

    public Saledto get(Long id){
        SalePost post=saleRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return Saledto.builder()
                .id(post.getId())
                .user(post.getUser())
                .title(post.getTitle())
                .content(post.getContent())
                .regDate(post.getRegDate())
                .filepath(post.getFilepath())
                .build();


    }

    public List<Saledto> getList(SaleSearch saleSearch){
        return saleRepository.getList(saleSearch).stream()
                .map(Saledto::new)
                .collect(Collectors.toList());
    }




    public List<Saledto> searchByTitle(String title) {
        return saleRepository.findByTitleContaining(title).stream()
                .map(Saledto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void update(Long id, Saledto update, MultipartFile file) throws IOException {
        SalePost salePost=saleRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        File deleteFile = new File(salePost.getFilepath());
        deleteFile.delete();

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        String savepath="/Users/seunghee/Documents/boardimages/sale/"+fileName;
        File saveFile = new File(savepath);
        file.transferTo(saveFile);

        salePost.toUpdateEntity(update,savepath);
        saleRepository.save(salePost);
    }

    public void delete(Long id){
        SalePost post=saleRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        File deleteFile = new File(post.getFilepath());
        deleteFile.delete();

        saleRepository.delete(post);
    }


}
