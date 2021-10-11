package com.github.weichun97.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The type Template d parse.
 *
 * @author chun
 * @date 2021 /9/30 11:44
 */
public class TemplateDParse {

    /**
     * 基础信息行
     */
    private static final Integer BASE_INFO_ROW_START_INDEX = 1;

    /**
     * 航班号所在列
     */
    private static final String BASE_INFO_FLIGHT_NUMBER_COLUMN_STR = "B";

    /**
     * 航班日期所在列
     */
    private static final String BASE_INFO_FLIGHT_DATE_COLUMN_STR = "D";

    /**
     * 联系人所在列
     */
    private static final String BASE_INFO_CONTACT_COLUMN_STR = "F";

    /**
     * 联系电话所在列
     */
    private static final String BASE_INFO_CONTACT_PHONE_COLUMN_STR = "H";

    /**
     * 信息的第一行（ULD表头下的一行）
     */
    private static final int ULD_START_ROW_INDEX = 4;

    /**
     * PACTL 行
     */
    private static final String PACTL_COLUMN_STR = "A";

    /**
     * PACTL 标识
     */
    private static final String PACTL_IDENTIFY = "PACTL:";

    /**
     * 解析模板
     *
     * @param inputStream the input stream
     * @return template ddto
     * @throws IOException            the io exception
     * @throws InvalidFormatException the invalid format exception
     */
    public static TemplateDDTO read(InputStream inputStream) throws IOException, InvalidFormatException {
        TemplateDDTO templateDDTO = new TemplateDDTO();
        List<TemplateDDTO.UldInfoDTO> uldInfoDTOList = new ArrayList<>();
        templateDDTO.setUldInfoDTOS(uldInfoDTOList);

        Workbook workBook = ExcelUtils.getWorkBook(inputStream);
        Sheet sheet = workBook.getSheetAt(0);

        // 解析基础信息
        parseBaseInfo(sheet, templateDDTO);

        // 解析 ULD 信息
        int currentRow = ULD_START_ROW_INDEX;
        int length = parseUld(sheet, currentRow, uldInfoDTOList);
        currentRow += length;

        return templateDDTO;
    }

    /**
     * 解析 ULD 并返回解析的长度（包括未解析到的也算）
     */
    private static int parseUld(Sheet sheet, int uldTitleNextRow, List<TemplateDDTO.UldInfoDTO> uldInfoDTOS) {
        Row row = sheet.getRow(ULD_START_ROW_INDEX);
        int length = 0;
        while (!isPactlRow(row)){
            if(row != null){
                if(CollUtil.isNotEmpty(uldInfoDTOS) && isMergeRegionAndBlank(sheet, row.getRowNum(), 0)){
                    parseAwbs(row, uldInfoDTOS.get(uldInfoDTOS.size() - 1).getAwbInfoDTOS());
                }
                else{
                    String uldNo = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("B")));
                    if(StrUtil.isNotBlank(uldNo)){
                        // 初始化 ULD 对象
                        TemplateDDTO.UldInfoDTO uldInfoDTO = new TemplateDDTO.UldInfoDTO();
                        List<TemplateDDTO.AwbInfoDTO> awbInfoDTOS = new ArrayList<>();
                        uldInfoDTO.setAwbInfoDTOS(awbInfoDTOS);

                        // 解析 ULD 基本信息
                        uldInfoDTO.setUldNo(uldNo);
                        String uldWeight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("C")));
                        String tareWeight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("E")));
                        uldInfoDTO.setUldWeight(!NumberUtil.isNumber(uldWeight) ? null : Double.valueOf(uldWeight));
                        uldInfoDTO.setTypeVersion(ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("D"))));
                        uldInfoDTO.setTareWeight(!NumberUtil.isNumber(tareWeight) ? null : Double.valueOf(tareWeight));
                        uldInfoDTOS.add(uldInfoDTO);

                        // 解析 Awb
                        parseAwbs(row, awbInfoDTOS);
                    }
                }
            }

            uldTitleNextRow ++;
            length ++;
            row = sheet.getRow(uldTitleNextRow);
        }

        return length;
    }

    /**
     * 解析 ULD 的 AWB
     * @param row
     * @param awbInfoDTOS
     */
    private static void parseAwbs(Row row, List<TemplateDDTO.AwbInfoDTO> awbInfoDTOS) {
        if(awbInfoDTOS == null){
            return;
        }
        String awbPrefix = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("F")));
        String awbNumber = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("G")));
        if(StrUtil.isNotBlank(awbPrefix) && StrUtil.isNotBlank(awbNumber)){
            TemplateDDTO.AwbInfoDTO awbInfoDTO = new TemplateDDTO.AwbInfoDTO();

            String loadingNumber = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("H")));
            String netLoading = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("I")));
            String totalNumberOfAwb = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("J")));

            awbInfoDTO.setAlwaysTheAwb(awbPrefix + awbNumber);
            awbInfoDTO.setLoadingNumber(!NumberUtil.isNumber(loadingNumber) ? null : Integer.valueOf(loadingNumber));
            awbInfoDTO.setNetLoading(!NumberUtil.isNumber(netLoading) ? null : Double.valueOf(netLoading));
            awbInfoDTO.setTotalNumberOfAwb(!NumberUtil.isNumber(totalNumberOfAwb) ? null : Integer.valueOf(totalNumberOfAwb));
            awbInfoDTO.setMemo(ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("K"))));
            awbInfoDTOS.add(awbInfoDTO);
        }



    }

    /**
     * 是合并单元格并且无数据
     *
     * @param sheet
     * @param row 行
     * @param column 列
     * @return bool
     */
    private static boolean isMergeRegionAndBlank(Sheet sheet, int row, int column) {
        return ExcelUtils.isMergedRegion(sheet, row, column) && StrUtil.isBlank(ExcelUtils.convertCellValueToString(sheet.getRow(row).getCell(column)));
    }

    /**
     * 是否 PACTL 行
     *
     * @param row
     * @return
     */
    private static boolean isPactlRow(Row row) {
        return row != null && ObjectUtil.equal(PACTL_IDENTIFY, ExcelUtils.convertCellValueToString(row.getCell(ExcelUtil.colNameToIndex(PACTL_COLUMN_STR))));
    }

    /**
     * 解析基础信息
     * 包括：航班号、航班日期、联系人、联系电话
     *
     * @param sheet
     * @param templateDDTO
     */
    private static void parseBaseInfo(Sheet sheet, TemplateDDTO templateDDTO) {
        templateDDTO.setFlightNumber(ExcelUtils.convertCellValueToString(sheet.getRow(BASE_INFO_ROW_START_INDEX).getCell(ExcelUtil.colNameToIndex(BASE_INFO_FLIGHT_NUMBER_COLUMN_STR))));
        templateDDTO.setFlightDate(ExcelUtils.convertCellValueToString(sheet.getRow(BASE_INFO_ROW_START_INDEX).getCell(ExcelUtil.colNameToIndex(BASE_INFO_FLIGHT_DATE_COLUMN_STR))));
        templateDDTO.setContact(ExcelUtils.convertCellValueToString(sheet.getRow(BASE_INFO_ROW_START_INDEX).getCell(ExcelUtil.colNameToIndex(BASE_INFO_CONTACT_COLUMN_STR))));
        templateDDTO.setContactPhone(ExcelUtils.convertCellValueToString(sheet.getRow(BASE_INFO_ROW_START_INDEX).getCell(ExcelUtil.colNameToIndex(BASE_INFO_CONTACT_PHONE_COLUMN_STR))));
    }
}
