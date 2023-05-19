package com.zipcook_server.controller;

import com.zipcook_server.data.dto.MainPagedto;
import com.zipcook_server.data.dto.recipe.Recipedto;
import com.zipcook_server.data.dto.sale.Saledto;
import com.zipcook_server.data.dto.share.Sharedto;
import com.zipcook_server.data.request.MainSearch;
import com.zipcook_server.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping(value="/main")
public class MainController {

    @Autowired
    MainService mainService;


    @GetMapping()
    public MainPagedto getPage(@ModelAttribute MainSearch mainSearch) {
        List<Recipedto> recipelist=mainService.recipegetList(mainSearch);
        List<Sharedto> sharelist=mainService.sharegetList(mainSearch);
        List<Saledto> salelist=mainService.salegetList(mainSearch);

        MainPagedto mainPagedto=MainPagedto.builder()
                .recipeList(recipelist)
                .shareList(sharelist)
                .saleList(salelist)
                .build();

        return mainPagedto;
    }
}
