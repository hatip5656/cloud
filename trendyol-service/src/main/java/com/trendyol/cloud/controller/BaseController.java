package com.trendyol.cloud.controller;

import com.trendyol.cloud.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    @Autowired
    ProductRepository productRepo;
    @Autowired
    CampaignRepository campaignRepo;
    @Autowired
    ChartsProductRepository chartsProductRepo;
    @Autowired
    ChartRepository chartRepo;
    @Autowired
    CouponRepository couponRepo;
    @Autowired
    CategoryRepository categoryRepo;


}
