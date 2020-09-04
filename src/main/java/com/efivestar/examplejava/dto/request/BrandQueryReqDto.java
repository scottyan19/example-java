package com.efivestar.examplejava.dto.request;

import com.efivestar.examplejava.jooq.meta.tables.records.BrandRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BrandQueryReqDto {

    Long id;

    @NotEmpty
    @Size(max = 50, min = 2)
    String name;

    Boolean deleted;

    public Boolean toRecord(BrandRecord record) {

        short changed = 0;
        if(name != null) {
            record.setName(name);
            changed++;
        }
        if(deleted!=null) {
            record.setDeleted(deleted);
            changed++;
        }

        return changed > 0;
    }
}
