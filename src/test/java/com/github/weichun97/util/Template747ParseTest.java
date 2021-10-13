package com.github.weichun97.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import junit.framework.TestCase;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;

public class Template747ParseTest extends TestCase {

    @Test
    public void testRead() throws IOException, InvalidFormatException {
        Template747DTO template747DTO1 = Template747Parse.read(new ClassPathResource("demo/template747/副本Y87454 SEPT 05 2021 FINAL DL - Copy（每周两份）.xlsx").getStream());
        assertEquals("Y87454", template747DTO1.getFlightNumber());
        assertEquals("SEP05", template747DTO1.getFlightDate());

        assertEquals("PMC30955", template747DTO1.getMaindeckUlds().get(0).getUldNo());
        assertEquals(3070D, template747DTO1.getMaindeckUlds().get(0).getWeight());
        assertEquals("A1", template747DTO1.getMaindeckUlds().get(0).getCntr());
        assertEquals("96", template747DTO1.getMaindeckUlds().get(0).getHgt());
        assertNull(template747DTO1.getMaindeckUlds().get(0).getRemarks());
        assertEquals("X", template747DTO1.getMaindeckUlds().get(0).getPos());
        assertNull(template747DTO1.getMaindeckUlds().get(0).getCk());

        assertEquals("PMC31667", template747DTO1.getLowerdeckUlds().get(0).getUldNo());
        assertEquals(2628D, template747DTO1.getLowerdeckUlds().get(0).getWeight());
        assertEquals("QM", template747DTO1.getLowerdeckUlds().get(0).getCntr());
        assertEquals("64", template747DTO1.getLowerdeckUlds().get(0).getHgt());
        assertNull(template747DTO1.getLowerdeckUlds().get(0).getRemarks());
        assertEquals("X", template747DTO1.getLowerdeckUlds().get(0).getPos());
        assertNull(template747DTO1.getLowerdeckUlds().get(0).getCk());
    }
}
