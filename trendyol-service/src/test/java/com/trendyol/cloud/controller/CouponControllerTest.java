package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.Coupon;
import com.trendyol.cloud.model.DiscountType;
import com.trendyol.cloud.model.ResponsePojo;
import com.trendyol.cloud.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Spy
    @Autowired
    ProductRepository productRepo;
    @Spy
    @Autowired
    CampaignRepository campaignRepo;
    @Spy
    @Autowired
    ChartsProductRepository chartsProductRepo;
    @Spy
    @Autowired
    ChartRepository chartRepo;
    @Spy
    @Autowired
    CouponRepository couponRepo;
    @Spy
    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    CouponController couponController;

    @Test
    @Order(1)
    public void testCreate() {
        Coupon coupon = new Coupon(1d, 10d, DiscountType.RATE);
        ResponsePojo result = couponController.create(coupon);
        try {
            Assertions.assertEquals(coupon.getDiscountAmount(), ((Coupon) result.getResponseBody()).getDiscountAmount());
        } finally {
            couponRepo.delete((Coupon) result.getResponseBody());
        }
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
        try {
            Assertions.assertEquals("Coupon with ID: " + coupon.getId() + " is deleted.", result.getResponseBody());
        } finally {
            couponRepo.delete(coupon);
        }
    }
}

