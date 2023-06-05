package com.zipcook_server.service;

import com.zipcook_server.data.dto.sale.SaleCreate;
import com.zipcook_server.data.dto.sale.Saledto;
import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.data.request.SaleMainSearch;
import com.zipcook_server.data.request.SaleSearch;
import com.zipcook_server.exception.PostNotFound;
import com.zipcook_server.repository.Sale.SaleRepository;
import com.zipcook_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SaleService {


    private final SaleRepository saleRepository;


    private final UserRepository userRepository;

    public void write(SaleCreate saleCreate, MultipartFile file) throws IOException {


        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        String savepath="/Users/seunghee/Documents/boardimages/"+fileName;
        File saveFile = new File(savepath);
        file.transferTo(saveFile);

        SalePost salePost = SalePost.builder()
                .nickname(saleCreate.getNickname())
                .title(saleCreate.getTitle())
                .content(saleCreate.getContent())
                .regDate(new Date())
                .price(saleCreate.getPrice())
                .discountPrice(saleCreate.getDiscountPrice())
                .place(saleCreate.getPlace())
                .filepath(savepath)
                .location(saleCreate.getLocation())
                .build();

        saleRepository.save(salePost);
    }

    public Saledto get(Long id,String location){
        SalePost post=saleRepository.findByIdAndLocation(id,location)
                .orElseThrow(PostNotFound::new);

        return Saledto.builder()
                .id(post.getId())
                .nickname(post.getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .regDate(post.getRegDate())
                .price(post.getPrice())
                .discountPrice(post.getDiscountPrice())
                .place(post.getPlace())
                .filepath(post.getFilepath())
                .build();


    }

    public List<Saledto> getList(SaleSearch saleSearch){
        return saleRepository.getList(saleSearch).stream()
                .map(Saledto::new)
                .collect(Collectors.toList());
    }


    public List<Saledto> getMainList(SaleMainSearch saleMainSearch){
        return saleRepository.getMainList(saleMainSearch).stream()
                .map(Saledto::new)
                .collect(Collectors.toList());
    }




    public List<Saledto> searchByTitle(String title,String location) {
        return saleRepository.findByTitleContainingAndLocation(title,location).stream()
                .map(Saledto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void update(Long id, String location,Saledto update, MultipartFile file) throws IOException {
        SalePost salePost=saleRepository.findByIdAndLocation(id,location)
                .orElseThrow(PostNotFound::new);

        File deleteFile = new File(salePost.getFilepath());
        deleteFile.delete();

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        String savepath="/Users/seunghee/Documents/boardimages/"+fileName;
        File saveFile = new File(savepath);
        file.transferTo(saveFile);

        salePost.toUpdateEntity(update,savepath);
        saleRepository.save(salePost);
    }

    public void delete(Long id,String location){
        SalePost post=saleRepository.findByIdAndLocation(id,location)
                .orElseThrow(PostNotFound::new);

        File deleteFile = new File(post.getFilepath());
        deleteFile.delete();

        saleRepository.delete(post);
    }


}
