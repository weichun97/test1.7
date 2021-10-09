package com.github.weichun97.util;

import cn.hutool.core.io.resource.ClassPathResource;
import junit.framework.TestCase;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;

public class TemplateAParseTest extends TestCase {

    @Test
    public void testReadTemplateA() throws IOException, InvalidFormatException {
        TemplateADTO templateADTO1 = TemplateAParse.read(new ClassPathResource("demo/templatea/金鹏航空复重表2021-09-23.xlsx").getStream());
        assertEquals("Y87915", templateADTO1.getFlightNumber());
        assertEquals("09-23", templateADTO1.getFlightDate());
        assertEquals(8, templateADTO1.getUldInfoDTOS().size());
        assertEquals("卢阳舟", templateADTO1.getLister());
        assertEquals("张金龙", templateADTO1.getReviewer());

        TemplateADTO templateADTO2 = TemplateAParse.read(new ClassPathResource("demo/templatea/Y87915-2021-09-27.xlsx").getStream());
        assertEquals("Y87915", templateADTO2.getFlightNumber());
        assertEquals("09-27", templateADTO2.getFlightDate());
        assertEquals(11, templateADTO2.getUldInfoDTOS().size());
        assertEquals("王攀超", templateADTO2.getLister());
        assertEquals("张力强", templateADTO2.getReviewer());

        TemplateADTO templateADTO3 = TemplateAParse.read(new ClassPathResource("demo/templatea/9-22 7915.xlsx").getStream());
        assertEquals("Y87915", templateADTO3.getFlightNumber());
        assertEquals("09-22", templateADTO3.getFlightDate());
        assertEquals(12, templateADTO3.getUldInfoDTOS().size());
        assertEquals("JD", templateADTO3.getLister());
        assertEquals("LX", templateADTO3.getReviewer());

        TemplateADTO templateADTO4 = TemplateAParse.read(new ClassPathResource("demo/templatea/Y87915-2021-09-27.xlsx").getStream());
        assertEquals("Y87915", templateADTO4.getFlightNumber());
        assertEquals("09-27", templateADTO4.getFlightDate());
        assertEquals(11, templateADTO4.getUldInfoDTOS().size());
        assertEquals("王攀超", templateADTO4.getLister());
        assertEquals("张力强", templateADTO4.getReviewer());
    }
}
