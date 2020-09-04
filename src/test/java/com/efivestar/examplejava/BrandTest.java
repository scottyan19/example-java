package com.efivestar.examplejava;

import com.efivestar.examplejava.model.Brand;
import com.efivestar.examplejava.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Scott Yan
 * @date 2020/09/04
 */
@SpringBootTest
@Slf4j
class BrandTest {

    @Resource
    private BrandService brandService;

    @Test
    void doTest() {
        var brand = Brand.builder()
                .name("test")
                .build();
        var insertResult = brandService.insert(brand);
        Assert.isTrue(insertResult.getSuccess(), "insert result should be true");
        Assert.isInstanceOf(Brand.class, insertResult.getData());
        var insertResultObject = (Brand)insertResult.getData();
        var queryObject = Brand.builder()
                .id(insertResultObject.getId())
                .build();
        var queryResult = brandService.query(queryObject, null);
        Assert.isTrue(queryResult.getSuccess(), "query result should be true");
        Assert.isInstanceOf(List.class, queryResult.getData());
        @SuppressWarnings("unchecked")
        var list = (List<Brand>)queryResult.getData();
        Assert.isTrue(list.size()==1);
        Assert.isTrue(list.get(0).getName().equals("test"));
        Assert.notNull(list.get(0).getCreatedDate());
        log.info(list.get(0).getLastModifiedDate().toString());
    }

}
