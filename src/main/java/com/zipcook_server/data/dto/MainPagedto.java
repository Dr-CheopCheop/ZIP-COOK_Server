package com.zipcook_server.data.dto;

import com.zipcook_server.data.dto.recipe.Recipedto;
import com.zipcook_server.data.dto.sale.Saledto;
import com.zipcook_server.data.dto.share.Sharedto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainPagedto {
    private List<Recipedto> recipeList;
    private List<Saledto> saleList;
    private List<Sharedto> shareList;

}