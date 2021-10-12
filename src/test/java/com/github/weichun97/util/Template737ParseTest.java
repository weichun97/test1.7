package com.github.weichun97.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import junit.framework.TestCase;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;

public class Template737ParseTest extends TestCase {

    @Test
    public void testRead() throws IOException, InvalidFormatException {
        Template737DTO template737DTO1 = Template737Parse.read(new ClassPathResource("demo/template737/Y87925 2021-09-06 复重表 （每周5份）.xlsx").getStream());
        assertEquals("Y87925", template737DTO1.getFlightNumber());
        assertEquals(DateUtil.parse("2021-09-06", "yyyy-MM-dd"), template737DTO1.getFlightDate());
        assertEquals("张雪飞", template737DTO1.getPreparer());
        assertEquals("周俊英", template737DTO1.getReviewer());

        assertEquals("LAK1063DHL", template737DTO1.getUldInfoDTOS().get(0).getUldNo());
        assertEquals(1910D, template737DTO1.getUldInfoDTOS().get(0).getSuttle());
        assertEquals(246D, template737DTO1.getUldInfoDTOS().get(0).getSelfWeight());
        assertEquals(60D, template737DTO1.getUldInfoDTOS().get(8).getPlateWight());
        assertEquals(2156D, template737DTO1.getUldInfoDTOS().get(0).getTheoreticalWeight());
        assertEquals(2135D, template737DTO1.getUldInfoDTOS().get(0).getGrossWeight());
        assertEquals("1M", template737DTO1.getUldInfoDTOS().get(9).getTypeVersion());
    }
}
