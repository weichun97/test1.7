package com.github.weichun97.util;

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

/**
 * The type Template 747 parse.
 *
 * @author chun
 * @date 2021 /9/30 11:44
 */
public class Template747Parse {

    /**
     * 基础信息行
     */
    private static final Integer BASE_INFO_ROW_START_INDEX = 5;

    /**
     * ULD开始行
     */
    private static final int MAINDECK_ULD_START_ROW_INDEX = 9;

    /**
     * 解析模板
     *
     * @param inputStream the input stream
     * @return the template adto
     * @throws IOException            the io exception
     * @throws InvalidFormatException the invalid format exception
     */
    public static Template747DTO read(InputStream inputStream) throws IOException, InvalidFormatException {
        Template747DTO template747DTO = new Template747DTO();
        template747DTO.setMaindeckUlds(new ArrayList<Template747DTO.UldInfoDTO>());
        template747DTO.setLowerdeckUlds(new ArrayList<Template747DTO.UldInfoDTO>());
        template747DTO.setBulks(new ArrayList<Template747DTO.UldInfoDTO>());

        Workbook workBook = ExcelUtils.getWorkBook(inputStream);
        Sheet sheet = workBook.getSheetAt(0);
        // 解析基础信息
        parseBaseInfo(sheet, template747DTO);

        // 解析主甲板 ULD 数据
        int currentRow = MAINDECK_ULD_START_ROW_INDEX;
        int length = parseMaindeckUlds(sheet, currentRow, template747DTO.getMaindeckUlds());
        currentRow += length;

        // 解析副甲板 ULD 数据
        currentRow += 3; // 跳过标题
        parseLowerdeckUlds(sheet, currentRow, template747DTO.getLowerdeckUlds());

        // 解析 BULK 数据
        currentRow += 4; // 跳过标题
        parseBulks(sheet, currentRow, template747DTO.getBulks());

        return template747DTO;
    }

    /**
     * 解析主甲板 ULD 信息并返回占用 excel 的行数
     *
     * @param sheet
     * @param lowerdeckUldTitleNextRow 主甲板 ULD 标题的下一行
     * @param lowerdeckUlds 数据容器
     * @return 占用的 excel 的行数
     */
    private static int parseLowerdeckUlds(Sheet sheet, int lowerdeckUldTitleNextRow, List<Template747DTO.UldInfoDTO> lowerdeckUlds) {
        Row row = sheet.getRow(lowerdeckUldTitleNextRow);
        int length = 0;
        while (!isEndOfLowerdeck(row)){
            String uldNo = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("D")));
            if(StrUtil.isNotBlank(uldNo)){
                Template747DTO.UldInfoDTO uldInfoDTO = new Template747DTO.UldInfoDTO();
                uldInfoDTO.setUldNo(uldNo);

                String weight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("G")));
                String cntr = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("I")));
                String hgt = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("J")));
                String remarks = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("K")));
                String pos = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("L")));
                String ck = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("M")));

                uldInfoDTO.setWeight(!NumberUtil.isNumber(weight) ? null : Double.parseDouble(weight));
                uldInfoDTO.setCntr(StrUtil.isBlank(cntr) ? null : cntr);
                uldInfoDTO.setHgt(StrUtil.isBlank(hgt) ? null : hgt);
                uldInfoDTO.setRemarks(StrUtil.isBlank(remarks) ? null : remarks);
                uldInfoDTO.setPos(StrUtil.isBlank(pos) ? null : pos);
                uldInfoDTO.setCk(StrUtil.isBlank(ck) ? null : ck);
                lowerdeckUlds.add(uldInfoDTO);
            }

            lowerdeckUldTitleNextRow ++;
            length ++;
            row = sheet.getRow(lowerdeckUldTitleNextRow);
        }
        return length;
    }

    /**
     * 是否是副甲板数据结束
     *
     * @param row
     * @return
     */
    private static boolean isEndOfLowerdeck(Row row) {
        return ObjectUtil.equal("X", ExcelUtils.convertCellValueToString(row.getCell(ExcelUtil.colNameToIndex("A"))));
    }

    /**
     * 解析主甲板 ULD 信息并返回占用 excel 的行数
     *
     * @param sheet
     * @param maindeckUldTitleNextRow 主甲板 ULD 标题的下一行
     * @param maindeckUlds 数据容器
     * @return 占用的 excel 的行数
     */
    private static int parseMaindeckUlds(Sheet sheet, int maindeckUldTitleNextRow, List<Template747DTO.UldInfoDTO> maindeckUlds) {
        Row row = sheet.getRow(maindeckUldTitleNextRow);
        int length = 0;
        while (!isEndOfMaindeck(row)){
            String uldNo = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("D")));
            if(StrUtil.isNotBlank(uldNo)){
                Template747DTO.UldInfoDTO uldInfoDTO = new Template747DTO.UldInfoDTO();
                uldInfoDTO.setUldNo(uldNo);

                String weight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("G")));
                String cntr = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("I")));
                String hgt = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("J")));
                String remarks = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("K")));
                String pos = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("L")));
                String ck = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("M")));

                uldInfoDTO.setWeight(!NumberUtil.isNumber(weight) ? null : Double.parseDouble(weight));
                uldInfoDTO.setCntr(StrUtil.isBlank(cntr) ? null : cntr);
                uldInfoDTO.setHgt(StrUtil.isBlank(hgt) ? null : hgt);
                uldInfoDTO.setRemarks(StrUtil.isBlank(remarks) ? null : remarks);
                uldInfoDTO.setPos(StrUtil.isBlank(pos) ? null : pos);
                uldInfoDTO.setCk(StrUtil.isBlank(ck) ? null : ck);
                maindeckUlds.add(uldInfoDTO);
            }

            maindeckUldTitleNextRow ++;
            length ++;
            row = sheet.getRow(maindeckUldTitleNextRow);
        }
        return length;
    }

    /**
     * 主甲板数据读取是否结束
     * 读取到 E 列值为 "MD TTL" 为结束
     *
     * @param row
     * @return
     */
    private static boolean isEndOfMaindeck(Row row) {
        return ObjectUtil.equal("MD TTL", ExcelUtils.convertCellValueToString(row.getCell(ExcelUtil.colNameToIndex("E"))));
    }

    /**
     * 解析空板并返回解析的长度（包括未解析到的也算）
     *
     * @param sheet sheet
     * @param bulkTitleNextRow bulk 标题的下一行
     * @param bulks 数据容器
     * @return 空板所占用的 excel 的行数
     */
    private static int parseBulks(Sheet sheet, int bulkTitleNextRow, List<Template747DTO.UldInfoDTO> bulks) {
        Row row = sheet.getRow(bulkTitleNextRow);
        int length = 0;
        while (!isEndOfBulk(row)){
            String uldNo = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("D")));
            if(StrUtil.isNotBlank(uldNo)){
                Template747DTO.UldInfoDTO uldInfoDTO = new Template747DTO.UldInfoDTO();
                uldInfoDTO.setUldNo(uldNo);

                String weight = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("G")));
                String cntr = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("I")));
                String hgt = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("J")));
                String remarks = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("K")));
                String pos = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("L")));
                String ck = ExcelUtils.convertCellValueToString(row.getCell(CellReference.convertColStringToIndex("M")));

                uldInfoDTO.setWeight(!NumberUtil.isNumber(weight) ? null : Double.parseDouble(weight));
                uldInfoDTO.setCntr(StrUtil.isBlank(cntr) ? null : cntr);
                uldInfoDTO.setHgt(StrUtil.isBlank(hgt) ? null : hgt);
                uldInfoDTO.setRemarks(StrUtil.isBlank(remarks) ? null : remarks);
                uldInfoDTO.setPos(StrUtil.isBlank(pos) ? null : pos);
                uldInfoDTO.setCk(StrUtil.isBlank(ck) ? null : ck);
                bulks.add(uldInfoDTO);
            }

            bulkTitleNextRow ++;
            length ++;
            row = sheet.getRow(bulkTitleNextRow);
        }
        return length;
    }

    /**
     * 是否是 BULK 的结束
     *
     * @param row
     * @return
     */
    private static boolean isEndOfBulk(Row row) {
        return ObjectUtil.equal("LD TTL", ExcelUtils.convertCellValueToString(row.getCell(ExcelUtil.colNameToIndex("E"))));
    }

    /**
     * 解析基础信息
     *
     * @param sheet
     * @param template747DTO
     */
    private static void parseBaseInfo(Sheet sheet, Template747DTO template747DTO) {
        String[] flightNumberAndDate = ExcelUtils.convertCellValueToString(sheet.getRow(BASE_INFO_ROW_START_INDEX).getCell(ExcelUtil.colNameToIndex("D"))).split(" ");
        if(flightNumberAndDate.length == 2){
            template747DTO.setFlightNumber(flightNumberAndDate[0].replace("-", ""));
            template747DTO.setFlightDate(flightNumberAndDate[1]);
        }
    }
}
