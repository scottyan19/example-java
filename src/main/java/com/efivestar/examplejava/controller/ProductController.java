package com.efivestar.examplejava.controller;

import com.efivestar.examplejava.dto.ResultDto;
import com.efivestar.examplejava.dto.request.ProductQueryReqDto;
import com.efivestar.examplejava.dto.request.ProductSaveReqDto;
import com.efivestar.examplejava.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResultDto get(ProductQueryReqDto dto, @PageableDefault(sort = "id") Pageable pageable) {
        return productService.query(dto, pageable);
    }

    @PostMapping
    public ResultDto post(@Valid @RequestBody ProductSaveReqDto dto) {
        return productService.insert(dto);
    }

    @PutMapping
    public ResultDto put(@RequestBody ProductSaveReqDto dto) {
        return productService.update(dto);
    }
}
