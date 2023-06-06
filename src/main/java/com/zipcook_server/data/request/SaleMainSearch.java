package com.zipcook_server.data.request;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class SaleMainSearch {

    private int page;



    public long getOffset(){
        return (long)((page-1)*5);
    }
}
