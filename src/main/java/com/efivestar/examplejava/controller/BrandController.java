package com.efivestar.examplejava.controller;

import com.efivestar.examplejava.dto.ResultDto;
import com.efivestar.examplejava.dto.request.BrandQueryReqDto;
import com.efivestar.examplejava.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@Slf4j
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ResultDto get(BrandQueryReqDto dto, @PageableDefault(sort = "id") Pageable pageable) {
        return brandService.query(dto, pageable);
    }

    @PostMapping
    public ResultDto post(@Valid @RequestBody BrandQueryReqDto dto) {
        return brandService.insert(dto);
    }

    @PutMapping
    public ResultDto put(@RequestBody BrandQueryReqDto dto) {
        return brandService.update(dto);
    }

    @DeleteMapping
    public ResultDto delete(@RequestBody BrandQueryReqDto dto) {
        return brandService.delete(dto);
    }

}
