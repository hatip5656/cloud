package com.trendyol.cloud.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/************************
 *    6.01.2020  *
 *    hatipaksunger  *
 ************************/
@Entity
@Data
@Where(clause = "deleted = false")
public class Coupon extends BaseEntity {
    //if it is a static amount
    private double discountAmount;
    //minimum chart amount
    private double minChartAmount;

    private DiscountType discountType;

    private boolean used;

    public Coupon() {

    }

    public Coupon(double minChartAmount,double discountAmount, DiscountType discountType) {
        this.discountAmount = discountAmount;
        this.minChartAmount = minChartAmount;
        this.discountType = discountType;
    }
}
