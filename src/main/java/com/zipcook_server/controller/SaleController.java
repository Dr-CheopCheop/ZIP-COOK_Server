package com.zipcook_server.controller;

import com.zipcook_server.data.dto.sale.SaleCreate;
import com.zipcook_server.data.dto.sale.SaleEdit;
import com.zipcook_server.data.dto.sale.SaleResponse;
import com.zipcook_server.data.request.SaleSearch;
import com.zipcook_server.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping(value="/board-sale")
public class SaleController {

    @Autowired
    SaleService saleService;

    @PostMapping()
    public ResponseEntity<String> salePost(@RequestBody @Valid SaleCreate saleCreate) throws IOException {
        saleService.write(saleCreate);
        return ResponseEntity.ok("Post shared successfully!");
    }

    @GetMapping("/{boardId}")
    public SaleResponse getPost(@PathVariable Long boardId) throws IOException {
        return saleService.get(boardId);

    }

    @GetMapping()
    public List<SaleResponse> getList(@ModelAttribute SaleSearch saleSearch){
        return saleService.getList(saleSearch);
    }

    @GetMapping("/search/{title}")
    public List<SaleResponse> searchByTitle(@PathVariable String title) throws IOException {
        return saleService.searchByTitle(title);
    }

    @PatchMapping("/{boardId}")
    public void edit(@PathVariable Long boardId, @RequestBody @Valid SaleEdit request){
        saleService.edit(boardId,request);

    }


    @DeleteMapping("/{boardId}")
    public void delete(@PathVariable Long boardId){
        saleService.delete(boardId);

    }


}


