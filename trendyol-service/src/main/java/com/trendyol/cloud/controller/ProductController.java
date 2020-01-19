package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.Product;
import com.trendyol.cloud.model.ResponsePojo;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    @PostMapping("/save")
    public ResponsePojo create(@RequestBody Product product) {
        try {
            product.setCategories(product.getCategories().stream().map(item -> {return item = categoryRepo.findById(item.getId()).get();}).collect(Collectors.toList()));
            return new ResponsePojo(productRepo.save(product));
        } catch (Exception e) {
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @PostMapping("/delete")
    public ResponsePojo delete(@RequestBody Product product) {
        try {
            productRepo.deleteById(product.getId());
            return new ResponsePojo("Product with ID: "+ product.getId() +" is deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @GetMapping("/list")
    public ResponsePojo list() {
        try {
            return new ResponsePojo(productRepo.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }


}
