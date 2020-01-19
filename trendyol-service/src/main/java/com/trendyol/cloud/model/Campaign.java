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
public class Campaign extends BaseEntity {
    @OneToOne
    Category category;
    //if it is a static amount
    private double discountAmount;

    private int  minChartItemCount;

    private DiscountType discountType;

    public Campaign(Category category, double discountAmount, int minChartItemCount, DiscountType discountType) {
        this.category = category;
        this.discountAmount = discountAmount;
        this.minChartItemCount = minChartItemCount;
        this.discountType = discountType;
    }

    public Campaign() {
    }
}
