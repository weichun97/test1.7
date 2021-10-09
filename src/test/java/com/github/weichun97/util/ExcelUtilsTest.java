package com.github.weichun97.util;


import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Assert;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ExcelUtilsTest {

    @Test
    public void testGetWorkBook() throws IOException, InvalidFormatException {
        Workbook workBook = ExcelUtils.getWorkBook(new ClassPathResource("demo/templatea/金鹏航空复重表2021-09-23.xlsx").getStream());
        Assert.notNull(workBook);
    }

    @Test
    public void test(){
        assertEquals(0, CellReference.convertColStringToIndex("A"));
        assertEquals("B", CellReference.convertNumToColString(1));
    }
}
