package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.*;
import com.trendyol.cloud.pojo.CouponApplyPojo;
import com.trendyol.cloud.repository.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class ChartControllerTest {
    public ChartControllerTest() {
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
    ChartController chartController;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    @Order(1)
    public void testCreate() {
        Category category = new Category();
        category.setTitle("Test");
        category = categoryRepo.saveAndFlush(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Product product = new Product("Test Product", 10, categories);
        product = productRepo.saveAndFlush(product);
        ChartsProduct chartsProduct = new ChartsProduct(product, 1);
        List<ChartsProduct> chartsProducts = new ArrayList<>();
        chartsProduct = chartsProductRepo.saveAndFlush(chartsProduct);
        chartsProducts.add(chartsProduct);
        Chart chart = new Chart();
        chart.setProductList(chartsProducts);
        ResponsePojo result = chartController.create(chart);
        try {
            Assertions.assertEquals(10, ((Chart) result.getResponseBody()).getAmount());
        } finally {
            chartRepo.delete((Chart) result.getResponseBody());
            jdbcTemplate.execute("DELETE from CHART_PRODUCTLIST where CHART_ID=" + chart.getId());
            chartsProductRepo.delete(chartsProduct);
            productRepo.delete(product);
            jdbcTemplate.execute("DELETE from PRODUCT_CATEGORIES where PRODUCT_ID=" + product.getId());
            categoryRepo.delete(category);
        }
    }

    @Test
    @Order(2)
    public void testAddToChart() {
        Category category = new Category();
        category.setTitle("Test");
        category = categoryRepo.saveAndFlush(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Product product = new Product("Test Product", 10, categories);
        product = productRepo.saveAndFlush(product);
        ChartsProduct chartsProduct = new ChartsProduct(product, 1);
        List<ChartsProduct> chartsProducts = new ArrayList<>();
        chartsProduct = chartsProductRepo.saveAndFlush(chartsProduct);
        chartsProducts.add(chartsProduct);
        Chart chart = new Chart();
        chart.setProductList(chartsProducts);
        chart = ((Chart) chartController.create(chart).getResponseBody());
        Chart chart1 = chart;
        ResponsePojo result = chartController.addToChart(chart1);
        try {
            Assertions.assertEquals(chart.getAmount() * 2, ((Chart) result.getResponseBody()).getAmount());
        } finally {
            chartRepo.delete((Chart) result.getResponseBody());
            jdbcTemplate.execute("DELETE from CHART_PRODUCTLIST where CHART_ID=" + chart.getId());
            chartsProductRepo.delete(chartsProduct);
            productRepo.delete(product);
            jdbcTemplate.execute("DELETE from PRODUCT_CATEGORIES where PRODUCT_ID=" + product.getId());
            categoryRepo.delete(category);
        }
    }

    @Test
    @Order(3)
    public void testApplyCoupon() {
        Category category = new Category();
        category.setTitle("Test");
        category = categoryRepo.saveAndFlush(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Product product = new Product("Test Product", 10, categories);
        product = productRepo.saveAndFlush(product);
        ChartsProduct chartsProduct = new ChartsProduct(product, 1);
        List<ChartsProduct> chartsProducts = new ArrayList<>();
        chartsProduct = chartsProductRepo.saveAndFlush(chartsProduct);
        chartsProducts.add(chartsProduct);
        Chart chart = new Chart();
        chart.setProductList(chartsProducts);
        chart = ((Chart) chartController.create(chart).getResponseBody());
        Coupon coupon = new Coupon(10, 2, DiscountType.AMOUNT);
        coupon = couponRepo.saveAndFlush(coupon);
        CouponApplyPojo couponApplyPojo = new CouponApplyPojo();
        couponApplyPojo.setChartId(chart.getId());
        couponApplyPojo.setCouponId(coupon.getId());
        ResponsePojo result = chartController.applyCoupon(couponApplyPojo);

        try {
            Assertions.assertEquals(8, ((Chart) result.getResponseBody()).getAmount());
        } finally {
            chartRepo.delete((Chart) result.getResponseBody());
            jdbcTemplate.execute("DELETE from CHART_PRODUCTLIST where CHART_ID=" + chart.getId());
            chartsProductRepo.delete(chartsProduct);
            productRepo.delete(product);
            jdbcTemplate.execute("DELETE from PRODUCT_CATEGORIES where PRODUCT_ID=" + product.getId());
            categoryRepo.delete(category);
            couponRepo.delete(coupon);
        }
    }

    @Test
    @Order(4)
    public void testList() {
        ResponsePojo result = chartController.list();
        Assertions.assertEquals(ArrayList.class, result.getResponseBody().getClass());
    }

    @Test
    @Order(5)
    public void testDelete() {
        Category category = new Category();
        category.setTitle("Test");
        category = categoryRepo.saveAndFlush(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Product product = new Product("Test Product", 10, categories);
        product = productRepo.saveAndFlush(product);
        ChartsProduct chartsProduct = new ChartsProduct(product, 1);
        List<ChartsProduct> chartsProducts = new ArrayList<>();
        chartsProduct = chartsProductRepo.saveAndFlush(chartsProduct);
        chartsProducts.add(chartsProduct);
        Chart chart = new Chart();
        chart.setProductList(chartsProducts);
        ResponsePojo chart1 = chartController.create(chart);
        ResponsePojo result = chartController.delete(((Chart) chart1.getResponseBody()));
        try {
            Assertions.assertEquals("Chart with ID: " + ((Chart) chart1.getResponseBody()).getId() + " is deleted.", result.getResponseBody());
        } finally {
            chartRepo.delete(chart);
            jdbcTemplate.execute("DELETE from CHART_PRODUCTLIST where CHART_ID=" + chart.getId());
            chartsProductRepo.delete(chartsProduct);
            productRepo.delete(product);
            jdbcTemplate.execute("DELETE from PRODUCT_CATEGORIES where PRODUCT_ID=" + product.getId());
            categoryRepo.delete(category);
        }
    }


}
