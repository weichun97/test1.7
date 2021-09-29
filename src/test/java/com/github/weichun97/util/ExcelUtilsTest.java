package com.github.weichun97.util;


import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Assert;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.IOException;

public class ExcelUtilsTest {

    @Test
    public void testReadTemplateA() throws IOException, InvalidFormatException {
        ExcelUtils.readTemplateA(new ClassPathResource("demo/金鹏航空复重表2021-09-23.xlsx").getStream());
    }

    @Test
    public void testGetWorkBook() throws IOException, InvalidFormatException {
        Workbook workBook = ExcelUtils.getWorkBook(new ClassPathResource("demo/金鹏航空复重表2021-09-23.xlsx").getStream());
        Assert.notNull(workBook);
    }
}
