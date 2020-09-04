package com.efivestar.examplejava;

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

    }

}
