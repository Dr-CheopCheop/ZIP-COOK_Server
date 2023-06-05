package com.zipcook_server.controller;

import com.zipcook_server.data.dto.sale.SaleCreate;
import com.zipcook_server.data.dto.sale.Saledto;
import com.zipcook_server.data.request.SaleSearch;
import com.zipcook_server.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@ResponseBody
@RequestMapping(value="/board-sale")
public class SaleController {

    @Autowired
    SaleService saleService;


    @PostMapping()
    public ResponseEntity<String> salePost(@RequestPart("salepost") @Valid SaleCreate saleCreate,
                                            @RequestPart(value="file",required = true) MultipartFile file) throws IOException {

        saleService.write(saleCreate,file);
        return ResponseEntity.ok("Post shared successfully!");
    }

    @GetMapping("/{location}/{boardId}")
    public Saledto getPost(@PathVariable String location,@PathVariable Long boardId) throws IOException {
        return saleService.get(boardId,location);

    }

    @GetMapping()
    public List<Saledto> getList(@ModelAttribute SaleSearch saleSearch){
        return saleService.getList(saleSearch);
    }

    @GetMapping("/{location}/search/{title}")
    public List<Saledto> searchByTitle(@PathVariable String location,@PathVariable String title) throws IOException {
        return saleService.searchByTitle(title,location);
    }

    @GetMapping("/{location}/update/{boardId}")
    public void updateForm(@PathVariable String location, @PathVariable Long boardId, Model model)  {
        Saledto saledto =saleService.get(boardId,location);
        model.addAttribute("saleboardupdate", saledto);
    }

    @PostMapping("/{location}/update/{boardId}")
    public ResponseEntity<String> update(@PathVariable String location,@PathVariable Long boardId, @RequestPart("salepost") @Valid Saledto update,
                                             @RequestPart(value="file",required = true) MultipartFile file) throws IOException {
        saleService.update(boardId,location,update,file);
        return ResponseEntity.ok("Updated Successfully!");
    }


    @DeleteMapping("/{location}/{boardId}")
    public void delete(@PathVariable String location,@PathVariable Long boardId){
        saleService.delete(boardId,location);

    }


}


