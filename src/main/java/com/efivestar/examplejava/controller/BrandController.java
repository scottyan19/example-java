package com.efivestar.examplejava.controller;

import com.efivestar.examplejava.common.ResultCode;
import com.efivestar.examplejava.dto.ResultDto;
import com.efivestar.examplejava.model.Brand;
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

    private void raiseEvent() {
        log.info(String.valueOf(System.currentTimeMillis()));
    }

    @GetMapping
    public ResultDto get(Brand brand, @PageableDefault(sort = "id") Pageable pageable) {
        return brandService.query(brand, pageable);
    }

    @PostMapping
    public ResultDto post(@Valid @RequestBody Brand brand) {
        return brandService.insert(brand);
    }

    @PutMapping
    public ResultDto put(@RequestBody Brand brand) {
        if(brand.getId()==null) {
            return ResultDto.error(ResultCode.ARGUMENT_INVALID, "brand id must be provided for update operation");
        }
        return brandService.update(brand);
    }

    @DeleteMapping
    public ResultDto delete(@RequestBody Brand brand) {
        if(brand.getId()==null) {
            return ResultDto.error(ResultCode.ARGUMENT_INVALID, "brand id must be provided for delete operation");
        }
        return brandService.delete(brand);
    }
}
