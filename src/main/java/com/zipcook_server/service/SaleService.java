package com.zipcook_server.service;

import com.zipcook_server.data.dto.sale.SaleCreate;
import com.zipcook_server.data.dto.sale.SaleEdit;
import com.zipcook_server.data.dto.sale.SaleResponse;
import com.zipcook_server.data.entity.SaleEditor;
import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.data.request.SaleSearch;
import com.zipcook_server.exception.PostNotFound;
import com.zipcook_server.repository.Sale.SaleRepository;
import com.zipcook_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    UserRepository userRepository;

    public void write(SaleCreate saleCreate) throws IOException {
        User user = userRepository.findById(saleCreate.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        SalePost salePost = SalePost.builder()
                .user(user)
                .title(saleCreate.getTitle())
                .content(saleCreate.getContent())
                .regDate(new Date())
                .build();

        saleRepository.save(salePost);
    }

    public SaleResponse get(Long id){
        SalePost post=saleRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return SaleResponse.builder()
                .id(post.getId())
                .user(post.getUser())
                .title(post.getTitle())
                .content(post.getContent())
                .regDate(post.getRegDate())
                .build();


    }

    public List<SaleResponse> getList(SaleSearch saleSearch){
        return saleRepository.getList(saleSearch).stream()
                .map(SaleResponse::new)
                .collect(Collectors.toList());
    }



    public List<SaleResponse> searchEntities(String keyword) {
        return saleRepository.findByTitleContaining(keyword).stream()
                .map(SaleResponse::new)
                .collect(Collectors.toList());
    }

    public List<SaleResponse> searchByTitle(String title) {
        return saleRepository.findByTitleContaining(title).stream()
                .map(SaleResponse::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void edit(Long id, SaleEdit saleEdit){
        SalePost salePost=saleRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        SaleEditor.SaleEditorBuilder saleEditorBuilder=salePost.toEditor();

        SaleEditor saleEditor=saleEditorBuilder.title(saleEdit.getTitle())
                .content(saleEdit.getContent())
                .build();

        salePost.edit(saleEditor);
    }

    public void delete(Long id){
        SalePost post=saleRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        saleRepository.delete(post);
    }


}
