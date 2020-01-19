package com.trendyol.cloud.repository;

import com.trendyol.cloud.model.Chart;
import com.trendyol.cloud.model.Coupon;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/************************
 *    6.01.2020  *
 *    hatipaksunger  *
 ************************/

public interface ChartRepository extends JpaRepository<Chart,Long> {

    @Override
    @Transactional
    @Modifying
    @Query("update Chart set deleted = 1 where id =:id")
    void deleteById(@Param("id") Long id);
}
