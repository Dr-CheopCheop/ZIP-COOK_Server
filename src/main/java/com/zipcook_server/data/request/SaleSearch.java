package com.zipcook_server.data.request;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class SaleSearch {

    //private int page;

    private String location;

    /**public long getOffset(){
        return (long)((page-1)*10);
    }
     **/
}
