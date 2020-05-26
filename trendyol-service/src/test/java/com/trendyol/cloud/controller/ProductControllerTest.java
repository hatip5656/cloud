package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.Category;
import com.trendyol.cloud.model.Product;
import com.trendyol.cloud.model.ResponsePojo;
import com.trendyol.cloud.repository.CategoryRepository;
import com.trendyol.cloud.repository.ProductRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Autowired
    ProductRepository productRepo;
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

        Assertions.assertEquals(product.getPrice(), ((Product) result.getResponseBody()).getPrice());
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

        Assertions.assertEquals("Product with ID: " + product.getId() + " is deleted.", result.getResponseBody());
    }

}
