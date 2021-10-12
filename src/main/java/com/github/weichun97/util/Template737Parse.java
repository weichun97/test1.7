package com.github.weichun97.util;

import cn.hutool.core.date.DateUtil;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chun
 * @date 2021/9/30 11:44
 */
public class Template737Parse {

    /**
     * ULD开始行
     */
    private static final int ULD_START_ROW_INDEX = 3;

    /**
     * 解析模板
     *
     * @param inputStream the input stream
     * @return the template adto
     * @throws IOException            the io exception
     * @throws InvalidFormatException the invalid format exception
     */
    public static Template737DTO read(InputStream inputStream) throws IOException, InvalidFormatException {
        Template737DTO template737DTO = new Template737DTO();
        List<Template737DTO.UldInfoDTO> uldInfoDTOS = new ArrayList<>();
        List<Template737DTO.UldInfoDTO> bulks = new ArrayList<>();
        template737DTO.setUldInfoDTOS(uldInfoDTOS);
        template737DTO.setBulks(bulks);

        Workbook workBook = ExcelUtils.getWorkBook(inputStream);
        Sheet sheet = workBook.getSheetAt(0);
        // 解析基础信息
        parseBaseInfo(sheet, template737DTO);

        // 解析ULD数据
        int currentRow = ULD_START_ROW_INDEX;
        int length = parseUld(sheet, currentRow, uldInfoDTOS);
        currentRow += length;

        // 解析空板数据
        currentRow++; // 跳过标题
        parseBulks(sheet, currentRow, bulks);

        return template737DTO;
    }

    /**
     * 解析空板并返回解析的长度（包括未解析到的也算）
     *
     * @param sheet sheet
     * @param bulkTitleNextRow bulk 标题的下一行
     * @param bulks 数据容器
     * @return 空板所占用的 excel 的行数
     */
    private static int parseBulks(Sheet sheet, int bulkTitleNextRow, List<Template737DTO.UldInfoDTO> bulks) {
        Row row = sheet.getRow(bulkTitleNextRow);
        int length = 0;
        while (!isTotalRow(row)){
            String uldNo = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("B")));
            if(StrUtil.isNotBlank(uldNo)){
                Template737DTO.UldInfoDTO uldInfoDTO = new Template737DTO.UldInfoDTO();
                uldInfoDTO.setUldNo(uldNo);

                String suttle = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("C")));
                String selfWeight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("D")));
                String plateWight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("E")));
                String theoreticalWeight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("F")));
                String grossWeight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("G")));

                uldInfoDTO.setSuttle(!NumberUtil.isNumber(suttle) ? null : Double.parseDouble(suttle));
                uldInfoDTO.setSelfWeight(!NumberUtil.isNumber(selfWeight) ? null : Double.parseDouble(selfWeight));
                uldInfoDTO.setPlateWight(!NumberUtil.isNumber(plateWight) ? null : Double.parseDouble(plateWight));
                uldInfoDTO.setTheoreticalWeight(!NumberUtil.isNumber(theoreticalWeight) ? null : Double.parseDouble(theoreticalWeight));
                uldInfoDTO.setGrossWeight(!NumberUtil.isNumber(grossWeight) ? null : Double.parseDouble(grossWeight));
                uldInfoDTO.setTypeVersion(ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("I"))));
                bulks.add(uldInfoDTO);
            }

            bulkTitleNextRow ++;
            length ++;
            row = sheet.getRow(bulkTitleNextRow);
        }
        return length;
    }

    /**
     * 是否是 TOTAL 行
     *
     * @param row
     * @return
     */
    private static boolean isTotalRow(Row row) {
        return ObjectUtil.equal("TOTAL", ExcelUtils.convertCellValueToString(row.getCell(ExcelUtil.colNameToIndex("B"))));
    }

    /**
     * 解析基础信息
     *
     * @param sheet
     * @param template737DTO
     */
    private static void parseBaseInfo(Sheet sheet, Template737DTO template737DTO) {
        String flightNumber = StrUtil.replace(ExcelUtils.convertCellValueToString(sheet.getRow(1).getCell(ExcelUtil.colNameToIndex("C"))), " ", "");
        String preparer = ExcelUtils.convertCellValueToString(sheet.getRow(1).getCell(ExcelUtil.colNameToIndex("G")));
        String reviewer = ExcelUtils.convertCellValueToString(sheet.getRow(1).getCell(ExcelUtil.colNameToIndex("I")));

        template737DTO.setFlightNumber(flightNumber);
        template737DTO.setFlightDate(sheet.getRow(1).getCell(ExcelUtil.colNameToIndex("E")).getDateCellValue());
        template737DTO.setPreparer(StrUtil.isBlank(preparer) ? null : preparer);
        template737DTO.setReviewer(StrUtil.isBlank(reviewer) ? null : reviewer);
    }

    /**
     * 解析 ULD 并返回解析的长度（包括未解析到的也算）
     * @param uldTitleNextRow uld标题的下一行
     */
    private static int parseUld(Sheet sheet, int uldTitleNextRow, List<Template737DTO.UldInfoDTO> uldInfoDTOS) {
        Row row = sheet.getRow(uldTitleNextRow);
        int length = 0;
        while (!isBulkTitleRow(row)){
            String uldNo = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("B")));
            if(StrUtil.isNotBlank(uldNo)){
                Template737DTO.UldInfoDTO uldInfoDTO = new Template737DTO.UldInfoDTO();
                uldInfoDTO.setUldNo(uldNo);

                String suttle = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("C")));
                String selfWeight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("D")));
                String plateWight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("E")));
                String theoreticalWeight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("F")));
                String grossWeight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("G")));

                uldInfoDTO.setSuttle(!NumberUtil.isNumber(suttle) ? null : Double.parseDouble(suttle));
                uldInfoDTO.setSelfWeight(!NumberUtil.isNumber(selfWeight) ? null : Double.parseDouble(selfWeight));
                uldInfoDTO.setPlateWight(!NumberUtil.isNumber(plateWight) ? null : Double.parseDouble(plateWight));
                uldInfoDTO.setTheoreticalWeight(!NumberUtil.isNumber(theoreticalWeight) ? null : Double.parseDouble(theoreticalWeight));
                uldInfoDTO.setGrossWeight(!NumberUtil.isNumber(grossWeight) ? null : Double.parseDouble(grossWeight));
                uldInfoDTO.setTypeVersion(ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("I"))));
                uldInfoDTOS.add(uldInfoDTO);
            }

            uldTitleNextRow ++;
            length ++;
            row = sheet.getRow(uldTitleNextRow);
        }
        return length;
    }

    /**
     * 是否是空板行
     *
     * @param row
     * @return
     */
    private static boolean isBulkTitleRow(Row row) {
        return ObjectUtil.equal("空板", ExcelUtils.convertCellValueToString(row.getCell(ExcelUtil.colNameToIndex("A"))));
    }

}
