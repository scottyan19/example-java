package com.efivestar.examplejava.service;

import com.efivestar.examplejava.common.ResultCode;
import com.efivestar.examplejava.dto.ResultDto;
import com.efivestar.examplejava.dto.request.BrandQueryReqDto;
import com.efivestar.examplejava.dto.response.BrandQueryResDto;
import com.efivestar.examplejava.utils.JqBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.efivestar.examplejava.jooq.meta.tables.Brand.BRAND;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {
    private final JqBuilder jqBuilder;

    public ResultDto query(BrandQueryReqDto dto, Pageable pageable) {
        var condition = jqBuilder.getConditionBuilder()
                .eq(dto.getId()!=null, BRAND.ID, dto.getId())
                .like(!StringUtils.isEmpty(dto.getName()), BRAND.NAME, dto.getName() + '%')
                .eq(dto.getDeleted()!=null, BRAND.DELETED, dto.getDeleted())
                .toCondition();
        try {
            var ctx = jqBuilder.getContext();
            var select = ctx
                    .select(BRAND.asterisk())
                    .from(BRAND)
                    .where(condition);
            var cnt = ctx.fetchCount(select);
            var data = select
                    .orderBy(jqBuilder.getSortFields(pageable.getSort(), BRAND))
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(BrandQueryResDto.class);
            return ResultDto.success(new PageImpl<>(data, pageable, cnt));
        } catch (Exception ex) {
            return ResultDto.error(ResultCode.DATABASE_ERROR, ex.getMessage());
        }
    }

    public ResultDto insert(BrandQueryReqDto dto) {
        try {
            var ctx = jqBuilder.getContext();
            var record = ctx.newRecord(BRAND);
            dto.toRecord(record);
            record.store();
            return ResultDto.success(BrandQueryResDto.builder().build().fromRecord(record));
        } catch (Exception ex) {
            return ResultDto.error(ResultCode.DATABASE_ERROR, ex.getMessage());
        }
    }

    public ResultDto update(BrandQueryReqDto dto) {
        if(dto.getId()==null) {
            return ResultDto.error(ResultCode.ARGUMENT_INVALID, "brand id must be provided for update.");
        }

        try {
            var ctx = jqBuilder.getContext();
            var record = ctx.fetchOne(BRAND, BRAND.ID.eq(dto.getId()));
            if(record == null) {
                return ResultDto.error(ResultCode.ARGUMENT_INVALID, "brand id is invalid");
            }
            if(dto.toRecord(record)){
                record.store();
            }
            return ResultDto.success();
        } catch (Exception ex) {
            return ResultDto.error(ResultCode.DATABASE_ERROR, ex.getMessage());
        }
    }

    public ResultDto delete(BrandQueryReqDto dto) {
        if(dto.getId()==null) {
            return ResultDto.error(ResultCode.ARGUMENT_INVALID, "brand id must be provided for update.");
        }

        try {
            var ctx = jqBuilder.getContext();
            var record = ctx.fetchOne(BRAND, BRAND.ID.eq(dto.getId()));
            if(record == null) {
                return ResultDto.error(ResultCode.ARGUMENT_INVALID, "brand id is invalid");
            }
            record.delete();
            return ResultDto.success();
        } catch (Exception ex) {
            return ResultDto.error(ResultCode.DATABASE_ERROR, ex.getMessage());
        }
    }

}
