package com.trendyol.cloud.service;

import com.trendyol.cloud.model.Chart;
import com.trendyol.cloud.model.ChartsProduct;
import lombok.Data;

/************************
 *    15.01.2020  *
 *    hatipaksunger  *
 ************************/
@Data
public class DeliveryCostCalculator{
    private double costPerDelivery = 0;
    private double costPerProduct = 0;
    private double fixedCost = 2.99;

    public double calculateFor( Chart chart) {
        chart.setDeliveryAmount(0);

        for (ChartsProduct chartsProduct : chart.getProductList()) {
            chart.setDeliveryAmount(chart.getDeliveryAmount() + (chartsProduct.getProduct().getDistinctCategories().size() * costPerDelivery) + (chartsProduct.getCount() * costPerProduct));
        }
        chart.setDeliveryAmount(chart.getDeliveryAmount() + fixedCost);
        return chart.getDeliveryAmount();
    }

    public DeliveryCostCalculator(double costPerDelivery, double costPerProduct, double fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    public DeliveryCostCalculator() {

    }
}
