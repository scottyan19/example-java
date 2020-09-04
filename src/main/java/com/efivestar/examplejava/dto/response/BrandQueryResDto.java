package com.efivestar.examplejava.dto.response;

import com.efivestar.examplejava.dto.request.BrandQueryReqDto;
import com.efivestar.examplejava.jooq.meta.tables.records.BrandRecord;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class BrandQueryResDto extends BrandQueryReqDto {
    public BrandQueryResDto fromRecord(BrandRecord record) {
        setId(record.getId());
        setName(record.getName());
        setDeleted(record.getDeleted());
        return this;
    }
}
