package com.github.weichun97.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import static org.apache.poi.ss.usermodel.CellType.FORMULA;

/**
 * The type Excel utils.
 */
public class ExcelUtils {
    private static final Log logger = LogFactory.getLog(ExcelUtils.class);

    /**
     * 获取 Workbook
     *
     * @param inputStream the input stream
     * @return work book
     * @throws IOException            the io exception
     * @throws InvalidFormatException the invalid format exception
     */
    public static Workbook getWorkBook(InputStream inputStream) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(inputStream);
    }

    /**
     * 将单元格内容转换为字符串
     * @param cell
     * @return
     */
    public static String convertCellValueToString(Cell cell) {
        if(cell==null){
            return null;
        }
        String returnValue = null;
//        if(cell.getCellTypeEnum() == FORMULA){
//            cell.getCachedFormulaResultTypeEnum();
//        }
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();

                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
                returnValue = df.format(doubleValue);
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                switch (cell.getCachedFormulaResultTypeEnum()) {
                    case BOOLEAN:
                        returnValue = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case NUMERIC:   //数字
                        Double doubleValue2 = cell.getNumericCellValue();

                        // 格式化科学计数法，取一位整数
                        DecimalFormat df2 = new DecimalFormat("0");
                        returnValue = df2.format(doubleValue2);
                        break;
                    case STRING:    //字符串
                        returnValue = cell.getStringCellValue();
                }
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }
}
