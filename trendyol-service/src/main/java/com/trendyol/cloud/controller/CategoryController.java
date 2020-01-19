package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.Category;
import com.trendyol.cloud.model.ResponsePojo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @PostMapping("/save")
    public ResponsePojo create(@RequestBody Category category) {
        try {
            return new ResponsePojo(categoryRepo.save(category));
        } catch (Exception e) {
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @PostMapping("/delete")
    public ResponsePojo delete(@RequestBody Category category) {
        try {
            categoryRepo.deleteById(category.getId());
            return new ResponsePojo("Category with ID: "+ category.getId() +" is deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @GetMapping("/list")
    public ResponsePojo list() {
        try {
            return new ResponsePojo(categoryRepo.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }


}
