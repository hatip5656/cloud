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
public class Category extends BaseEntity {
    private String title;

    @OneToOne
    private Category parentCategory;

    public Category() {
    }

    public Category(String title) {
        this.title = title;
    }

    public Category(String title, Category parentCategory) {
        this.title = title;
        this.parentCategory = parentCategory;
    }

}
