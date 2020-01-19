package com.trendyol.cloud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

import static com.trendyol.cloud.TrendyolServiceApplication.distinctByKey;

/************************
 *    6.01.2020  *
 *    hatipaksunger  *
 ************************/
@Entity
@Data
@Where(clause = "deleted = false")
public class Product extends BaseEntity {
    private String name;
    private double price;
    @ManyToMany(fetch = FetchType.EAGER)
    List<Category> categories;

    public Product(String name, double price, List<Category> categories) {
        this.name = name;
        this.price = price;
        this.categories = categories;
    }

    public Product() {
    }

    @JsonIgnore
    public List<Category> getDistinctCategories() {
        return this.categories.stream().filter(distinctByKey(Category::getId)).collect(Collectors.toList()) ;
    }


}
