package com.trendyol.cloud.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.stream.Collectors;

import static com.trendyol.cloud.TrendyolServiceApplication.distinctByKey;

/************************
 *    15.01.2020  *
 *    hatipaksunger  *
 ************************/
@Entity
@Data
@Where(clause = "deleted = false")
public class ChartsProduct extends BaseEntity {
    @OneToOne
    Product product;
    int count;

    public ChartsProduct(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public ChartsProduct() {
    }
}
