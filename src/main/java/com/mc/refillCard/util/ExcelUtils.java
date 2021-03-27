package com.mc.refillCard.util;

import org.apache.poi.ss.usermodel.*;

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

}
