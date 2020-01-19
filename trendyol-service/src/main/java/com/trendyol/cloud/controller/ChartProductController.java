package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.ChartsProduct;
import com.trendyol.cloud.model.ResponsePojo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chartProduct")
public class ChartProductController extends BaseController {

    @PostMapping("/save")
    public ResponsePojo create(@RequestBody ChartsProduct chartProduct) {
        try {
            return new ResponsePojo(chartsProductRepo.save(chartProduct));
        } catch (Exception e) {
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @PostMapping("/delete")
    public ResponsePojo delete(@RequestBody ChartsProduct chartProduct) {
        try {
            chartsProductRepo.deleteById(chartProduct.getId());
            return new ResponsePojo("ChartsProduct with ID: "+ chartProduct.getId() +" is deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @GetMapping("/list")
    public ResponsePojo list() {
        try {
            return new ResponsePojo(chartsProductRepo.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }


}
