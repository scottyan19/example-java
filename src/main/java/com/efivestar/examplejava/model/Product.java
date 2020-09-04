package com.efivestar.examplejava.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseAuditable{

    @Column(length = 32, nullable = false)
    private String code;

    @Column(length = 200, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;
}
