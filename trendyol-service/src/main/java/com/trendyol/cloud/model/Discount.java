package com.trendyol.cloud.model;

import lombok.Data;

/************************
 *    15.01.2020  *
 *    hatipaksunger  *
 ************************/
@Data
public class Discount {
    private DiscountType discountType;
    private double amount;
    private int minChartItem;

    public Discount(DiscountType discountType, double amount, int minChartItem) {
        this.discountType = discountType;
        this.amount = amount;
        this.minChartItem = minChartItem;
    }

    public Discount() {
    }
}
