package com.zipcook_server.service;

import com.zipcook_server.data.dto.recipe.Recipedto;
import com.zipcook_server.data.dto.sale.Saledto;
import com.zipcook_server.data.dto.share.Sharedto;
import com.zipcook_server.data.request.MainSearch;
import com.zipcook_server.repository.Recipe.RecipeRepository;
import com.zipcook_server.repository.Sale.SaleRepository;
import com.zipcook_server.repository.Share.ShareRepository;
import com.zipcook_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    ShareRepository shareRepository;


    public List<Recipedto> recipegetList(MainSearch mainSearch){
        return recipeRepository.maingetList(mainSearch).stream()
                .map(Recipedto::new)
                .collect(Collectors.toList());
    }

    public List<Sharedto> sharegetList(MainSearch mainSearch){
        return shareRepository.maingetList(mainSearch).stream()
                .map(Sharedto::new)
                .collect(Collectors.toList());
    }

    public List<Saledto> salegetList(MainSearch mainSearch){
        return saleRepository.maingetList(mainSearch).stream()
                .map(Saledto::new)
                .collect(Collectors.toList());
    }

}
