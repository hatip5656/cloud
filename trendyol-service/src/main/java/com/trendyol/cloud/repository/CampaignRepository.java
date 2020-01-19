package com.trendyol.cloud.repository;

import com.trendyol.cloud.model.Campaign;
import com.trendyol.cloud.model.Category;
import com.trendyol.cloud.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/************************
 *    6.01.2020  *
 *    hatipaksunger  *
 ************************/
public interface CampaignRepository extends JpaRepository<Campaign,Long> {

    List<Campaign> findAllByCategory(Category category);

    @Override
    @Transactional
    @Modifying
    @Query("update Campaign set deleted = 1 where id =:id")
    void deleteById(@Param("id") Long id);
}
