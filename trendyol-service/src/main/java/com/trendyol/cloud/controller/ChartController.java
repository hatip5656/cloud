package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.*;
import com.trendyol.cloud.pojo.CouponApplyPojo;
import com.trendyol.cloud.service.DeliveryCostCalculator;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chart")
public class ChartController extends BaseController {

    @PostMapping("/save")
    public ResponsePojo create(@RequestBody Chart chart) {
        try {
            DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(5, 1.5, 2.99);
            List<Discount> discounts = new ArrayList<>();
            for (ChartsProduct chartsProduct : chart.getProductList()) {
                chartsProduct.setProduct(productRepo.findById(chartsProduct.getProduct().getId()).get());
                chartsProduct = chartsProductRepo.saveAndFlush(chartsProduct);
                for (Category category : chartsProduct.getProduct().getCategories()) {
                    for (Campaign campaign : campaignRepo.findAllByCategory(category)) {
                        Discount discount = new Discount();
                        discount.setAmount(campaign.getDiscountAmount());
                        discount.setDiscountType(campaign.getDiscountType());
                        discount.setMinChartItem(campaign.getMinChartItemCount());
                        discounts.add(discount);
                    }
                }
            }
            chart.setAmount(chart.getAmountWithoutDiscount());
            chart.setAmount(chart.getDiscountedAmount(discounts));
            double deliveryCost = deliveryCostCalculator.calculateFor(chart);
            chart.setDeliveryAmount(deliveryCost);
            return new ResponsePojo(chartRepo.save(chart));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @PostMapping("/addToChart")
    public ResponsePojo addToChart(@RequestBody Chart chart) {
        try {
            DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(5, 1.5, 2.99);

            for (ChartsProduct chartsProduct1 : chart.getProductList()) {
                chartsProduct1.setProduct(productRepo.findById(chartsProduct1.getProduct().getId()).get());
                chartsProduct1 = chartsProductRepo.saveAndFlush(chartsProduct1);
            }
            Chart existing = chartRepo.findById(chart.getId()).get();
            for (ChartsProduct product : chart.getProductList()) {
                existing.addItem(product.getProduct(), product.getCount());
            }
            List<Discount> discounts = new ArrayList<>();
            existing.setProductList(chartsProductRepo.saveAll(existing.getProductList()));
            for (ChartsProduct chartsProduct : existing.getProductList()) {
                for (Category category : chartsProduct.getProduct().getCategories()) {
                    for (Campaign campaign : campaignRepo.findAllByCategory(category)) {
                        Discount discount = new Discount();
                        discount.setAmount(campaign.getDiscountAmount());
                        discount.setDiscountType(campaign.getDiscountType());
                        discount.setMinChartItem(campaign.getMinChartItemCount());
                        discounts.add(discount);
                    }
                }
            }
            existing.setAmount(existing.getAmountWithoutDiscount());
            existing.setAmount(existing.getDiscountedAmount(discounts));
            double deliveryCost = deliveryCostCalculator.calculateFor(existing);
            existing.setDeliveryAmount(deliveryCost);

            return new ResponsePojo(chartRepo.save(existing));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @PostMapping("/applyCoupon")
    public ResponsePojo applyCoupon(@RequestBody CouponApplyPojo couponApplyPojo) {
        try {
            Chart chart = chartRepo.findById(couponApplyPojo.getChartId()).get();
            Coupon coupon = couponRepo.findById(couponApplyPojo.getCouponId()).get();
            double amount = chart.getAmount();
            if (chart.getCouponAppliedAmount(coupon) != amount) {
                if (coupon.isUsed()) {
                    return new ResponsePojo(200,"Used Coupon!",chart,response);
                }
                coupon.setUsed(true);
                couponRepo.save(coupon);
                return new ResponsePojo(200,"Coupon successfully applied ", chartRepo.save(chart),response);
            } else {
                return new ResponsePojo(200,"Chart amount is not enough",chart,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @PostMapping("/delete")
    public ResponsePojo delete(@RequestBody Chart chart) {
        try {
            chartRepo.deleteById(chart.getId());
            return new ResponsePojo("Chart with ID: " + chart.getId() + " is deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @GetMapping("/list")
    public ResponsePojo list() {
        try {
            return new ResponsePojo(chartRepo.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }


}
