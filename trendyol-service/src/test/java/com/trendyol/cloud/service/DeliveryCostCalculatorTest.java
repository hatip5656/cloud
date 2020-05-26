package com.trendyol.cloud.service;

import com.trendyol.cloud.model.Category;
import com.trendyol.cloud.model.Chart;
import com.trendyol.cloud.model.ChartsProduct;
import com.trendyol.cloud.model.Product;
import org.junit.Test;
import org.springframework.context.annotation.Description;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeliveryCostCalculatorTest {
    DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(5d, 3d, 2.99d);

    @Test
    public void testCalculateFor() throws Exception {
        Category category = new Category();
        category.setTitle("Test");
        category.setId(1);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Product product = new Product("Test Product", 10, categories);
        product.setId(1);
        ChartsProduct chartsProduct = new ChartsProduct(product, 1);
        List<ChartsProduct> chartsProducts = new ArrayList<>();
        chartsProduct.setId(1);
        chartsProducts.add(chartsProduct);
        Chart chart = new Chart();
        chart.setProductList(chartsProducts);
        chart.setAmount(chart.getAmountWithoutDiscount());
        double result = deliveryCostCalculator.calculateFor(chart);
        assertEquals(10.99d, result,0.001);
    }

}
