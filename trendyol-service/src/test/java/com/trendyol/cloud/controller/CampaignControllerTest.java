package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.Campaign;
import com.trendyol.cloud.model.Category;
import com.trendyol.cloud.model.DiscountType;
import com.trendyol.cloud.model.ResponsePojo;
import com.trendyol.cloud.repository.CampaignRepository;
import com.trendyol.cloud.repository.CategoryRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/************************
 *    18.01.2020  *
 *    hatipaksunger  *
 ************************/
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "localtest")
public class CampaignControllerTest {
    public CampaignControllerTest() {
    }

    @Autowired
    CampaignRepository campaignRepo;
    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    CampaignController campaignController;


    @Test
    @Order(1)
    public void testCreate() {
        Category category = new Category();
        category.setId(1);
        category.setTitle("Technology");
        category = categoryRepo.save(category);
        Campaign campaign = new Campaign(category, 10d, 3, DiscountType.RATE);
        campaign.setId(9999);

        ResponsePojo result = campaignController.create(campaign);
        ResponsePojo waitedResult = new ResponsePojo(campaignRepo.findById(((Campaign) result.getResponseBody()).getId()).get());
        Assertions.assertEquals(waitedResult, result);
    }


    @Test
    @Order(2)
    public void testList() {
        ResponsePojo result = campaignController.list();
        List<Campaign> awaitining = campaignRepo.findAll();
        Assertions.assertEquals(new ResponsePojo(awaitining), result);
    }

    @Test
    @Order(3)
    public void testDelete() {
        Campaign campaign = new Campaign();
        campaign.setId(9999);
        ResponsePojo result = campaignController.delete(campaign);
        Assertions.assertEquals("Campaign with ID: " + campaign.getId() + " is deleted.", result.getResponseBody());
    }


}

