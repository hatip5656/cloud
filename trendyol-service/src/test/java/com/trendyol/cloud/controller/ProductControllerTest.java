package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.Category;
import com.trendyol.cloud.model.Product;
import com.trendyol.cloud.model.ResponsePojo;
import com.trendyol.cloud.repository.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
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
import java.util.List;

/************************
 *    18.01.2020  *
 *    hatipaksunger  *
 ************************/
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "localtest")
public class ProductControllerTest {
    public ProductControllerTest() {
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
    ProductController productController;


    @Test
    @Order(1)
    public void testCreate() {
        Category category = new Category();
        category.setTitle("Test");
        category = categoryRepo.saveAndFlush(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Product product = new Product("Test Product", 10, categories);
        ResponsePojo result = productController.create(product);
        try {
            Assertions.assertEquals(product.getPrice(), ((Product) result.getResponseBody()).getPrice());
        } finally {
            productRepo.delete((Product) result.getResponseBody());
        }
    }


    @Test
    @Order(2)
    public void testList() {
        ResponsePojo result = productController.list();
        Assertions.assertEquals(ArrayList.class, result.getResponseBody().getClass());
    }

    @Test
    @Order(3)
    public void testDelete() {
        Category category = new Category();
        category.setTitle("Test");
        category = categoryRepo.saveAndFlush(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Product product = new Product("Test Product", 10, categories);
        product = ((Product) productController.create(product).getResponseBody());

        ResponsePojo result = productController.delete(product);
        try {
            Assertions.assertEquals("Product with ID: " + product.getId() + " is deleted.", result.getResponseBody());
        } finally {
            productRepo.delete(product);
        }
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme