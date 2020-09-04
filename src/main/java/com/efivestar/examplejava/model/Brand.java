package com.efivestar.examplejava.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Brand extends BaseAuditable {

    @Size(max = 50, min = 2, message = "brand name length must be between 2 and 50")
    @NotBlank(message = "brand name cannot be null")
    @Column(length = 50, nullable = false)
    private String name;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;
}
