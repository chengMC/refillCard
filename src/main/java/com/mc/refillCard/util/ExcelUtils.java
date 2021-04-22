package com.mc.refillCard.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author: MC
 * @Date2020-12-21
 */

public class ExcelUtils {

    // 大标题的样式
    public static CellStyle bigTitle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
//        font.setFontName("宋体");
//        font.setFontHeightInPoints((short) 16);
        // 字体加粗
//        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 横向居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 纵向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     *  居中样式
     *
     * @param wb
     * @return
     */
    public static CellStyle bigCenter(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        // 横向居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 纵向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public static String geCellValue(Cell cell) {
        String result ="";
        if(cell == null){
            return result;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case Cell.CELL_TYPE_FORMULA:
                result =  cell.getCellFormula();
            case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                result =  cell.getStringCellValue();
            case Cell.CELL_TYPE_STRING:
                result =  cell.getStringCellValue();
            default:
                result =  "";
        }
        if(result.indexOf(" ")>-1){
            result = result.replace(" ","");
        }
        return result;
    }


    public static String geXSSFCellValue(XSSFCell cell) {
        String result ="";
        if(cell == null){
            return result;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case Cell.CELL_TYPE_FORMULA:
                result =  cell.getCellFormula();
            case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                result =  cell.getStringCellValue();
            case Cell.CELL_TYPE_STRING:
                result =  cell.getStringCellValue();
            default:
                result =  "";
        }
        if(result.indexOf(" ")>-1){
            result = result.replace(" ","");
        }
        return result;
    }

    public static String geHSSFCellValue(HSSFCell cell) {
        String result ="";
        if(cell == null){
            return result;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case Cell.CELL_TYPE_FORMULA:
                result =  cell.getCellFormula();
            case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                result =  cell.getStringCellValue();
            case Cell.CELL_TYPE_STRING:
                result =  cell.getStringCellValue();
            default:
                result =  "";
        }
        if(result.indexOf(" ")>-1){
            result = result.replace(" ","");
        }
        return result;
    }

    /**
     * xls/xlsx都使用的Workbook
     *
     * @param
     * @return
     * @author
     */
    public static Workbook readExcel(MultipartFile multipartFile) {
        Workbook wb = null;
        if (multipartFile == null) {
            return null;
        }
        String fileName = multipartFile.getOriginalFilename().trim();
        String extString = fileName.substring(fileName.lastIndexOf("."));
        try {
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(multipartFile.getInputStream());
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(multipartFile.getInputStream());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

}
