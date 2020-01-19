package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.Coupon;
import com.trendyol.cloud.model.ResponsePojo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
public class CouponController extends BaseController {

    @PostMapping("/save")
    public ResponsePojo create(@RequestBody Coupon coupon) {
        try {
            return new ResponsePojo(couponRepo.save(coupon));
        } catch (Exception e) {
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @PostMapping("/delete")
    public ResponsePojo delete(@RequestBody Coupon coupon) {
        try {
            couponRepo.deleteById(coupon.getId());
            return new ResponsePojo("Coupon with ID: "+ coupon.getId() +" is deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @GetMapping("/list")
    public ResponsePojo list() {
        try {
            return new ResponsePojo(couponRepo.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }


}
