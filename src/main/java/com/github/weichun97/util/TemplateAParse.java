package com.github.weichun97.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chun
 * @date 2021/9/30 11:44
 */
public class TemplateAParse {

    /**
     * 航班信息行
     */
    private static final int FLIGHT_INFO_ROW_INDEX = 0;

    /**
     * 航班信息列
     */
    private static final String FLIGHT_INFO_COLUMN_STR = "A";

    /**
     * 航班信息正则表达式
     */
    private static final Pattern FLIGHT_INFO_PATTERN = Pattern.compile("^\\s*([A-Z0-9]+)\\s+(\\d{2}-\\d{2})\\s*$", Pattern.CASE_INSENSITIVE);;

    /**
     * ULD信息的第一行（ULD表头下的一行）
     */
    private static final int ULD_START_ROW_INDEX = 2;

    /**
     * 制表人列
     */
    private static final String LIST_COLUMN_STR = "D";

    /**
     * 复核人列
     */
    private static final String REVIEWER_COLUMN_STR = "F";

    /**
     * 解析模板
     *
     * @param inputStream the input stream
     * @return the template adto
     * @throws IOException            the io exception
     * @throws InvalidFormatException the invalid format exception
     */
    public static TemplateADTO read(InputStream inputStream) throws IOException, InvalidFormatException {
        TemplateADTO templateADTO = new TemplateADTO();
        List<TemplateADTO.UldInfoDTO> uldInfoDTOS = new ArrayList<>();
        templateADTO.setUldInfoDTOS(uldInfoDTOS);

        Workbook workBook = ExcelUtils.getWorkBook(inputStream);
        Sheet sheet = workBook.getSheetAt(0);
        // 解析航班号和日期
        parseFlightInfo(sheet, templateADTO);

        // 解析ULD数据
        int currentRow = ULD_START_ROW_INDEX;
        int length = parseUld(sheet, currentRow, uldInfoDTOS);
        currentRow += length;

        // 解析制表人
        parseLister(sheet, currentRow, templateADTO);

        // 解析复核人
        parseReviewer(sheet, currentRow, templateADTO);

        return templateADTO;
    }

    /**
     * 解析复核人
     *
     * @param sheet
     * @param templateADTO
     */
    private static void parseReviewer(Sheet sheet, int currentRow, TemplateADTO templateADTO) {
        templateADTO.setReviewer(sheet.getRow(currentRow).getCell(CellReference.convertColStringToIndex(REVIEWER_COLUMN_STR)).getStringCellValue());
    }

    /**
     * 解析制表人
     *
     * @param sheet
     * @param templateADTO
     */
    private static void parseLister(Sheet sheet, int currentRow, TemplateADTO templateADTO) {
        templateADTO.setLister(sheet.getRow(currentRow).getCell(CellReference.convertColStringToIndex(LIST_COLUMN_STR)).getStringCellValue());
    }

    /**
     * 解析航班信息
     * @param sheet
     * @param templateADTO
     */
    private static void parseFlightInfo(Sheet sheet, TemplateADTO templateADTO) {
        String flightInfo = sheet.getRow(FLIGHT_INFO_ROW_INDEX).getCell(CellReference.convertColStringToIndex(FLIGHT_INFO_COLUMN_STR)).getStringCellValue();
        Matcher matcher = FLIGHT_INFO_PATTERN.matcher(flightInfo);
        if(matcher.matches()){
            templateADTO.setFlightNumber(matcher.group(1));
            templateADTO.setFlightDate(matcher.group(2));
        }
    }

    /**
     * 解析 ULD 并返回解析的长度（包括未解析到的也算）
     * @param uldTitleNextRow uld标题的下一行
     */
    private static int parseUld(Sheet sheet, int uldTitleNextRow, List<TemplateADTO.UldInfoDTO> uldInfoDTOS) {
        Row row = sheet.getRow(uldTitleNextRow);
        int length = 0;
        while (!isListerRow(row)){
            String uldNo = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("B")));
            if(StrUtil.isNotBlank(uldNo)){
                TemplateADTO.UldInfoDTO uldInfoDTO = new TemplateADTO.UldInfoDTO();
                uldInfoDTO.setUldNo(uldNo);

                String suttle = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("D")));
                String selfWeight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("E")));
                String plateWight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("F")));
                String theoreticalWeight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("G")));
                String grossWeight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("H")));

                uldInfoDTO.setSuttle(!NumberUtil.isNumber(suttle) ? null : Double.parseDouble(suttle));
                uldInfoDTO.setSelfWeight(!NumberUtil.isNumber(selfWeight) ? null : Double.parseDouble(selfWeight));
                uldInfoDTO.setPlateWight(!NumberUtil.isNumber(plateWight) ? null : Double.parseDouble(plateWight));
                uldInfoDTO.setTheoreticalWeight(!NumberUtil.isNumber(theoreticalWeight) ? null : Double.parseDouble(theoreticalWeight));
                uldInfoDTO.setGrossWeight(!NumberUtil.isNumber(grossWeight) ? null : Double.parseDouble(grossWeight));
                uldInfoDTO.setMemo(ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("I"))));
                uldInfoDTOS.add(uldInfoDTO);
            }

            uldTitleNextRow ++;
            length ++;
            row = sheet.getRow(uldTitleNextRow);
        }
        return length;
    }

    /**
     * 是否制表人行
     *
     * @param row
     * @return
     */
    private static boolean isListerRow(Row row) {
        return ObjectUtil.equal("制表人", StrUtil.trim(row.getCell(1).getStringCellValue()));
    }
}
