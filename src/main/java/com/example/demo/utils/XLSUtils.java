package com.example.demo.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.io.IOException;
import java.util.Objects;

public class XLSUtils {

    private XLSUtils() {
    }

    public static Sheet createSheet(SXSSFWorkbook workbook, String sheetName, String[] header) {
        Sheet sheet = workbook.createSheet(sheetName);
        XSSFFont titleFont = (XSSFFont) workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 11);
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(titleFont);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < header.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(header[i]);
        }
        return sheet;
    }

    public static synchronized void writeRow(Sheet sheet, Object[] arr) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        int columnCount = 0;
        for (Object obj : arr) {
            Cell cell = row.createCell(columnCount);
            if (obj instanceof String) {
                cell.setCellValue((String) obj);
            } else if (obj instanceof Long) {
                cell.setCellValue((Long) obj);
            } else if (obj instanceof Double) {
                cell.setCellValue((Double) obj);
            }
            columnCount++;
        }
    }

    public static void closeWorkbook(final SXSSFWorkbook workbook) {
        try {
            if (Objects.nonNull(workbook)) {
                workbook.close();
                workbook.dispose();
            }
        } catch (IOException e) {
        }
    }

}
