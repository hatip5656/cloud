package com.trendyol.cloud.tests;

import com.trendyol.cloud.controller.ProductController;
import com.trendyol.cloud.model.Category;
import com.trendyol.cloud.model.Product;
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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/************************
 *    18.01.2020  *
 *    hatipaksunger  *
 ************************/
@SpringBootTest
@RunWith(SpringRunner.class)
class ProductControllerTest {
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
    void testCreate() {
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
    void testDelete() {
        Category category = new Category();
        category.setTitle("Test");
        category = categoryRepo.saveAndFlush(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Product product = new Product("Test Product", 10, categories);
        product = ((Product) productController.create(product).getResponseBody());

        ResponsePojo result = productController.delete(product);
        try {
            Assertions.assertEquals("Product with ID: "+ product.getId() +" is deleted.", result.getResponseBody());
        } finally {
            productRepo.delete(product);
        }
    }

    @Test
    void testList() {
        ResponsePojo result = productController.list();
        Assertions.assertEquals(ArrayList.class, result.getResponseBody().getClass());
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme