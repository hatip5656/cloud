package com.trendyol.cloud.repository;

import com.trendyol.cloud.model.Campaign;
import com.trendyol.cloud.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/************************
 *    6.01.2020  *
 *    hatipaksunger  *
 ************************/
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Override
    @Transactional
    @Modifying
    @Query("update Category set deleted = 1 where id =:id")
    void deleteById(@Param("id") Long id);
}
