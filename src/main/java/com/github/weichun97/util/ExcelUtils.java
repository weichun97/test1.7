package com.github.weichun97.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;

public class ExcelUtils {

    private static final Log logger = LogFactory.getLog(ExcelUtils.class);

    public static void readTemplateA(InputStream inputStream) throws IOException, InvalidFormatException {
        Workbook workBook = getWorkBook(inputStream);
        Sheet sheet = workBook.getSheetAt(0);
        int currentRow = 1;
        List<> = parseUld(currentRow);
        Cell cell = sheet.getRow(1).getCell(1);
    }

    /**
     * 获取 Workbook
     * @param inputStream
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static Workbook getWorkBook(InputStream inputStream) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(inputStream);
    }
}
