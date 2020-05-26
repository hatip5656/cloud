package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.Coupon;
import com.trendyol.cloud.model.DiscountType;
import com.trendyol.cloud.model.ResponsePojo;
import com.trendyol.cloud.repository.CouponRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/************************
 *    18.01.2020  *
 *    hatipaksunger  *
 ************************/
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "localtest")
public class CouponControllerTest {
    public CouponControllerTest() {
    }

    @Autowired
    CouponRepository couponRepo;
    @Autowired
    CouponController couponController;

    @Test
    @Order(1)
    public void testCreate() {
        Coupon coupon = new Coupon(1d, 10d, DiscountType.RATE);
        ResponsePojo result = couponController.create(coupon);
        Assertions.assertEquals(coupon.getDiscountAmount(), ((Coupon) result.getResponseBody()).getDiscountAmount());

    }

    @Test
    @Order(2)
    public void testList() {
        ResponsePojo result = couponController.list();
        Assertions.assertEquals(ArrayList.class, result.getResponseBody().getClass());
    }

    @Test
    @Order(3)
    public void testDelete() {
        Coupon coupon = new Coupon(1d, 10d, DiscountType.RATE);
        coupon = ((Coupon) couponController.create(coupon).getResponseBody());
        ResponsePojo result = couponController.delete(coupon);

        Assertions.assertEquals("Coupon with ID: " + coupon.getId() + " is deleted.", result.getResponseBody());

    }
}

