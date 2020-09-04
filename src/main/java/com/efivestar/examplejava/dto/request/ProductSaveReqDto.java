package com.efivestar.examplejava.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaveReqDto {
    private Long id;
    @NotBlank(message = "product name cannot be null")
    @Size(max = 200, min = 2, message = "product name length must be between 2 and 200")
    private String name;
    @NotNull(message = "brand id cannot be null")
    @Min(value = 1, message = "brand id must be a positive integer")
    private Long brandId;
    @Builder.Default
    private Boolean deleted = false;
}
