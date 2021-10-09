package com.github.weichun97.util;

import cn.hutool.core.io.resource.ClassPathResource;
import junit.framework.TestCase;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;

public class TemplateDParseTest extends TestCase {

    @Test
    public void testRead() throws IOException, InvalidFormatException {
        TemplateDDTO templateDDTO1 = TemplateDParse.read(new ClassPathResource("demo/templated/Y8.xlsx").getStream());
        assertEquals("Y8-7951", templateDDTO1.getFlightNumber());
        assertEquals("25.SEP", templateDDTO1.getFlightDate());
        assertNull(templateDDTO1.getContact());
        assertEquals("夜间舱位协调：20826389,20826306\n" +
                "现场交货公机：13816020282", templateDDTO1.getContactPhone());
    }
}
