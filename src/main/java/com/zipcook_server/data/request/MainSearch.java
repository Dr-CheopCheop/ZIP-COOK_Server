package com.zipcook_server.data.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class MainSearch {

    private int sharepage;


    private int salepage;


    private int recipepage;

    private String location;


    public long sharegetOffset(){
        return (long)((sharepage-1)*5);
    }

    public long salegetOffset(){
        return (long)((salepage-1)*5);
    }

    public long recipegetOffset(){
        return (long)((recipepage-1)*5);
    }
}
