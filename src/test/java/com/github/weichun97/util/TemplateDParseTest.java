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
        assertEquals(9, templateDDTO1.getUldInfoDTOS().size());
        assertEquals("PAG20171H4", templateDDTO1.getUldInfoDTOS().get(0).getUldNo());
        assertEquals(1422D, templateDDTO1.getUldInfoDTOS().get(0).getUldWeight());
        assertEquals("MD", templateDDTO1.getUldInfoDTOS().get(0).getTypeVersion());
        assertEquals(112D, templateDDTO1.getUldInfoDTOS().get(0).getTareWeight());
        assertEquals("615-41599456", templateDDTO1.getUldInfoDTOS().get(0).getAwbInfoDTOS().get(0).getAlwaysTheAwb());
        assertEquals(63, templateDDTO1.getUldInfoDTOS().get(0).getAwbInfoDTOS().get(0).getLoadingNumber().intValue());
        assertEquals(1310D, templateDDTO1.getUldInfoDTOS().get(0).getAwbInfoDTOS().get(0).getNetLoading());
        assertEquals(297, templateDDTO1.getUldInfoDTOS().get(0).getAwbInfoDTOS().get(0).getTotalNumberOfAwb().intValue());
        assertEquals("“DHL快件（自DHL仓库出货）” ", templateDDTO1.getUldInfoDTOS().get(0).getAwbInfoDTOS().get(0).getMemo());

        TemplateDDTO templateDDTO2 = TemplateDParse.read(new ClassPathResource("demo/templated/Y8(1).xlsx").getStream());
        assertEquals("Y8-7951", templateDDTO2.getFlightNumber());
        assertEquals("23.SEP", templateDDTO2.getFlightDate());
        assertNull(templateDDTO2.getContact());
        assertEquals("夜间舱位协调：20826389,20826306\n" +
                "现场交货公机：13816020282", templateDDTO2.getContactPhone());
        assertEquals(9, templateDDTO2.getUldInfoDTOS().size());
        assertEquals("PAG50073Y8", templateDDTO2.getUldInfoDTOS().get(0).getUldNo());
        assertEquals(1102D, templateDDTO2.getUldInfoDTOS().get(0).getUldWeight());
        assertEquals("MD", templateDDTO2.getUldInfoDTOS().get(0).getTypeVersion());
        assertEquals(120D, templateDDTO2.getUldInfoDTOS().get(0).getTareWeight());
        assertEquals("615-94016580", templateDDTO2.getUldInfoDTOS().get(0).getAwbInfoDTOS().get(0).getAlwaysTheAwb());
        assertEquals(492, templateDDTO2.getUldInfoDTOS().get(0).getAwbInfoDTOS().get(0).getLoadingNumber().intValue());
        assertEquals(982D, templateDDTO2.getUldInfoDTOS().get(0).getAwbInfoDTOS().get(0).getNetLoading());
        assertEquals(1188, templateDDTO2.getUldInfoDTOS().get(0).getAwbInfoDTOS().get(0).getTotalNumberOfAwb().intValue());
        assertEquals("“DHL快件（自DHL仓库出货）” ", templateDDTO2.getUldInfoDTOS().get(0).getAwbInfoDTOS().get(0).getMemo());
    }
}
