package com.efivestar.examplejava.service;

import com.efivestar.examplejava.common.ResultCode;
import com.efivestar.examplejava.dto.ResultDto;
import com.efivestar.examplejava.model.Brand;
import com.efivestar.examplejava.repository.BrandRepository;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {
    private final BrandRepository brandRepository;

    public ResultDto query(Brand brand, Pageable pageable) {
        Specification<Brand> specification = Specifications.<Brand>and()
                .eq(!StringUtils.isEmpty(brand.getId()), "id", brand.getId())
                .eq(brand.getDeleted()!=null, "deleted", brand.getDeleted())
                .like(!StringUtils.isEmpty(brand.getName()), "name", brand.getName()+ '%')
                .build();
        ResultDto resultDto = ResultDto.success();

        try {
            if(pageable != null) {
                resultDto.setData(brandRepository.findAll(specification, pageable));
            } else {
                resultDto.setData(brandRepository.findAll(specification));
            }
        } catch (Exception ex) {
            resultDto = ResultDto.error(ResultCode.DATABASE_ERROR, ex.getMessage());
        }

        return resultDto;
    }

    public ResultDto insert(Brand brand) {
        return persist(brand);
    }

    public ResultDto update(Brand brand) {
        var entityOptional = brandRepository.findById(brand.getId());
        if (entityOptional.isEmpty()) {
            return ResultDto.error(ResultCode.ARGUMENT_INVALID, "brand id is invalid");
        }
        var entity = entityOptional.get();
        if(!StringUtils.isEmpty(brand.getName())) {
            entity.setName(brand.getName());
        }
        if(brand.getDeleted()!=null) {
            entity.setDeleted(brand.getDeleted());
        }

        return persist(entity);
    }

    public ResultDto delete(Brand brand){
        var entityOptional = brandRepository.findById(brand.getId());
        if (entityOptional.isEmpty()) {
            return ResultDto.error(ResultCode.ARGUMENT_INVALID, "brand id is invalid");
        }
        var entity = entityOptional.get();
        try {
            brandRepository.delete(brand);
        } catch (Exception ex) {
            return ResultDto.error(ResultCode.DATABASE_ERROR, ex.getMessage());
        }

        return ResultDto.success();
    }

    private ResultDto persist(Brand brand) {
        try {
            brandRepository.save(brand);
        } catch (Exception ex) {
            return ResultDto.error(ResultCode.DATABASE_ERROR, ex.getMessage());
        }

        return ResultDto.success(brand);
    }
}
