package com.trendyol.cloud.controller;

import com.trendyol.cloud.model.Campaign;
import com.trendyol.cloud.model.Product;
import com.trendyol.cloud.model.ResponsePojo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaign")
public class CampaignController extends BaseController {

    @PostMapping("/save")
    public ResponsePojo create(@RequestBody Campaign campaign) {
        try {
            campaign.setCategory(categoryRepo.findById(campaign.getCategory().getId()).get());
            return new ResponsePojo(campaignRepo.save(campaign));
        } catch (Exception e) {
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @PostMapping("/delete")
    public ResponsePojo delete(@RequestBody Campaign campaign) {
        try {
            campaignRepo.deleteById(campaign.getId());
            return new ResponsePojo("Campaign with ID: "+ campaign.getId() +" is deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }

    @GetMapping("/list")
    public ResponsePojo list() {
        try {
            return new ResponsePojo(campaignRepo.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponsePojo(500, e.getLocalizedMessage(), response);
        }
    }


}
