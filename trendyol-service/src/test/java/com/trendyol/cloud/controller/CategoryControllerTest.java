package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.Category;
import com.trendyol.cloud.model.ResponsePojo;
import com.trendyol.cloud.repository.CategoryRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/************************
 *    18.01.2020  *
 *    hatipaksunger  *
 ************************/
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "localtest")
public class CategoryControllerTest {
    public CategoryControllerTest() {
    }

    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    CategoryController categoryController;


    @Test
    @Order(1)
    public void testCreate() {
        Category category = new Category("Test");
        ResponsePojo result = categoryController.create(category);
        try {
            Assertions.assertEquals("Test", ((Category) result.getResponseBody()).getTitle());
        } finally {
            categoryRepo.delete(((Category) result.getResponseBody()));
        }
    }


    @Test
    @Order(2)
    public void testList() {
        ResponsePojo result = categoryController.list();
        Assertions.assertEquals(categoryRepo.findAll(), result.getResponseBody());
    }

    @Test
    @Order(3)
    public void testDelete() {
        Category category = new Category("Test");
        category.setId(9999);
        categoryRepo.save(category);
        ResponsePojo result = categoryController.delete(category);
        try {
            Assertions.assertEquals("Category with ID: " + category.getId() + " is deleted.", result.getResponseBody());
        } finally {
            categoryRepo.delete(category);
        }

    }
}
