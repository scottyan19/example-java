package com.efivestar.examplejava.service;

import com.efivestar.examplejava.common.ResultCode;
import com.efivestar.examplejava.dto.ResultDto;
import com.efivestar.examplejava.dto.request.ProductQueryReqDto;
import com.efivestar.examplejava.dto.request.ProductSaveReqDto;
import com.efivestar.examplejava.dto.response.ProductDetailResDto;
import com.efivestar.examplejava.model.Product;
import com.efivestar.examplejava.repository.BrandRepository;
import com.efivestar.examplejava.repository.ProductRepository;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.function.Function;


/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    public ResultDto query(ProductQueryReqDto dto, Pageable pageable) {
        assert pageable != null;
        Specification<Product> specification = Specifications.<Product>and()
                .eq(!StringUtils.isEmpty(dto.getCode()), "code", dto.getCode())
                .eq(dto.getDeleted() != null, "deleted", dto.getDeleted())
                .like(!StringUtils.isEmpty(dto.getBrandName()), "brand.name", dto.getBrandName() + '%')
                .like(!StringUtils.isEmpty(dto.getName()), "name", dto.getName() + '%')
                .build();
        ResultDto resultDto = ResultDto.success();

        Function<Product, ProductDetailResDto> mapper = p -> ProductDetailResDto.builder()
                .brandName(p.getBrand().getName())
                .id(p.getId())
                .deleted(p.getDeleted())
                .code(p.getCode())
                .name(p.getName())
                .build();

        try {
            resultDto.setData(productRepository
                    .findAll(specification, pageable)
                    .map(mapper));
        } catch (Exception ex) {
            resultDto.setSuccess(false);
            resultDto.setMessage(ex.getMessage());
        }

        return resultDto;
    }

    public ResultDto insert(ProductSaveReqDto dto) {
        var brandOptional = brandRepository.findById(dto.getBrandId());
        if(brandOptional.isEmpty()){
            return ResultDto.error(ResultCode.ARGUMENT_INVALID, "brand id is invalid.");
        }
        var entity = Product.builder()
                .brand(brandOptional.get())
                .deleted(dto.getDeleted())
                .code(getNewCode())
                .name(dto.getName())
                .build();
        return persist(entity);
    }

    public ResultDto update(ProductSaveReqDto dto) {
        var entityOptional = productRepository.findById(dto.getId());
        if (entityOptional.isEmpty()) {
            return ResultDto.error(ResultCode.ARGUMENT_INVALID, "product id is invalid");
        }
        var entity = entityOptional.get();
        if(!StringUtils.isEmpty(dto.getName())) {
            entity.setName(dto.getName());
        }
        if(dto.getDeleted()!=null) {
            entity.setDeleted(dto.getDeleted());
        }
        if(dto.getBrandId()!=null) {
            var brandOptional = brandRepository.findById(dto.getBrandId());
            if(brandOptional.isEmpty()){
                return ResultDto.error(ResultCode.ARGUMENT_INVALID, "brand id is invalid.");
            }
            entity.setBrand(brandOptional.get());
        }

        return persist(entity);
    }

    private String getNewCode() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    private ResultDto persist(Product product) {
        try{
            productRepository.save(product);
        } catch (Exception ex) {
            return ResultDto.error(ResultCode.DATABASE_ERROR, ex.getMessage());
        }

        return ResultDto.success();
    }
}
