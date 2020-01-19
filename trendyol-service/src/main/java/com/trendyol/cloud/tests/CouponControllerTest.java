package com.trendyol.cloud.tests;

import com.trendyol.cloud.controller.ChartController;
import com.trendyol.cloud.controller.CouponController;
import com.trendyol.cloud.model.Category;
import com.trendyol.cloud.model.Coupon;
import com.trendyol.cloud.model.DiscountType;
import com.trendyol.cloud.model.ResponsePojo;
import com.trendyol.cloud.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

/************************
 *    18.01.2020  *
 *    hatipaksunger  *
 ************************/
@SpringBootTest
@RunWith(SpringRunner.class)
class CouponControllerTest {
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
    void testCreate() {
        Coupon coupon = new Coupon(1d, 10d, DiscountType.RATE);
        ResponsePojo result = couponController.create(coupon);
        try {
            Assertions.assertEquals(coupon.getDiscountAmount(), ((Coupon) result.getResponseBody()).getDiscountAmount());
        } finally {
            couponRepo.delete((Coupon) result.getResponseBody());
        }
    }

    @Test
    void testDelete() {
        Coupon coupon = new Coupon(1d, 10d, DiscountType.RATE);
        coupon = ((Coupon) couponController.create(coupon).getResponseBody());
        ResponsePojo result = couponController.delete(coupon);
        try {
            Assertions.assertEquals("Coupon with ID: "+ coupon.getId() +" is deleted.", result.getResponseBody());
        } finally {
            couponRepo.delete(coupon);
        }
    }

    @Test
    void testList() {
        ResponsePojo result = couponController.list();
        Assertions.assertEquals(ArrayList.class, result.getResponseBody().getClass());
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme