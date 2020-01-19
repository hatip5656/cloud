package com.trendyol.cloud.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/************************
 *    6.01.2020  *
 *    hatipaksunger  *
 ************************/
@Entity
@Data
@Where(clause = "deleted = false")
public class Chart extends BaseEntity {
    @OneToMany(fetch = FetchType.EAGER,orphanRemoval = true)
    private List<ChartsProduct> productList;
    private double amount;
    private double deliveryAmount;

    public List<ChartsProduct> addItem(Product product, int count) {
        ChartsProduct chartsProduct1 = productList.stream()
                .filter(x -> product.getId()==x.getProduct().getId())
                .findAny()
                .orElse(null);
        if (chartsProduct1 != null) {
            productList.stream().forEach(chartsProduct -> {
                if (chartsProduct.getId() == chartsProduct1.getId()) {
                    chartsProduct.setCount(chartsProduct.getCount() + count);
                }
            });
        } else {
            ChartsProduct chartsProduct = new ChartsProduct(product, count);
            this.productList.add(chartsProduct);
        }
        return this.productList;
    }

    public double getAmountWithoutDiscount() {
        Double sum = this.productList.stream().mapToDouble(item -> item.getCount() * item.getProduct().getPrice()).sum();
        this.amount = sum;
        return  sum;
    }

    public double getDiscountedAmount(List<Discount> discounts) {
        discounts.forEach(discount -> {
            if (discount.getDiscountType() == DiscountType.AMOUNT) {
                if (this.productList.stream().mapToInt(i -> i.getCount()).sum() >= discount.getMinChartItem()) {
                    this.amount -= discount.getAmount();
                    System.out.println("Applied Discount : "+discount.getAmount());
                }
            } else if (discount.getDiscountType() == DiscountType.RATE) {
                this.amount -= this.amount * discount.getAmount() / 100;
                System.out.println("Applied Discount : "+  this.amount * discount.getAmount() / 100);
            }
        });
        return this.amount;
    }
    public double getCouponAppliedAmount(Coupon coupon) {
        if (this.amount >= coupon.getMinChartAmount()) {
            this.amount -= coupon.getDiscountAmount();
        }
        return this.amount;
    }

    public Chart() {
    }


}
