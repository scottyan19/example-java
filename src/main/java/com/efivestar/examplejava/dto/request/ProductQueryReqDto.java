package com.efivestar.examplejava.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductQueryReqDto {
    private Long id;
    private String name;
    private String code;
    private String brandName;
    private Boolean deleted;
}
