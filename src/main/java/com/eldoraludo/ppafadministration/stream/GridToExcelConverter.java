package com.eldoraludo.ppafadministration.stream;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.grid.GridDataSource;
import org.joda.time.DateMidnight;

import java.util.Date;
import java.util.List;

public class GridToExcelConverter {

    private HSSFCellStyle dateStyle;
    private HSSFCellStyle dateMidnightStyle;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFRow currentRow;
    private HSSFCell currentCell;

    public HSSFWorkbook convertGridToExcelWorkbook(BeanModel model, GridDataSource source) {
        workbook = new HSSFWorkbook();
        dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("m/d/yy h:mm"));
        dateMidnightStyle = workbook.createCellStyle();
        dateMidnightStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("m/d/yy"));
        sheet = workbook.createSheet("1");
        initSheet();
        generateHeader(model);
        generateRows(model, source);
        sizeColumns(model);
        return workbook;
    }

    private void initSheet() {
        sheet.setAutobreaks(true);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setFitHeight((short) 1);
        printSetup.setFitWidth((short) 1);
    }

    private void generateHeader(BeanModel model) {
        List<String> propertyNames = model.getPropertyNames();

        CellStyle headerStyle = createHeaderStyle();
        currentRow = sheet.createRow(0);
        int cellNb = 0;
        for (String property : propertyNames) {
            if (model.get(property).getConduit() != null) {
                Cell cell = currentRow.createCell(cellNb);
                cell.setCellValue(model.get(property).getLabel());
                cell.setCellStyle(headerStyle);
                cellNb++;
            }
        }
    }

    private void generateRows(BeanModel model, GridDataSource source) {
        List<String> propertyNames = model.getPropertyNames();
        for (int i = 0; i < source.getAvailableRows(); i++) {
            newRow(model, source, propertyNames, i);
        }
    }

    private void newRow(BeanModel model, GridDataSource source, List<String> propertyNames, int i) {
        currentRow = sheet.createRow(i + 1);
        Object rowValue = source.getRowValue(i);
        int columnIndex = 0;
        for (String property : propertyNames) {
            PropertyModel propertyModel = model.get(property);
            if (propertyModel.getConduit() != null) {
                newCell(propertyModel, columnIndex, rowValue);
                columnIndex++;
            }
        }
    }

    private void newCell(PropertyModel propertyModel, int columnIndex, Object rowValue) {
        currentCell = currentRow.createCell(columnIndex);
        Object value = propertyModel.getConduit().get(rowValue);
        if (isDate(propertyModel) && null != value) {
            createDateCell((Date) value);
        } else if (isDateMidnight(propertyModel) && null != value) {
            createDateMidnightCell((DateMidnight) value);
        } else {
            createStringCell(value);
        }
    }

    private void createStringCell(Object value) {
        if (value != null) {
            currentCell.setCellValue(value.toString());
        }
    }

    private void createDateCell(Date date) {
        currentCell.setCellValue(date);
        currentCell.setCellStyle(dateStyle);
    }

    private void createDateMidnightCell(DateMidnight date) {
        currentCell.setCellValue(date.toDate());
        currentCell.setCellStyle(dateMidnightStyle);
    }

    private boolean isDate(PropertyModel propertyModel) {
        return "date".equals(propertyModel.getDataType());
    }

    private boolean isDateMidnight(PropertyModel propertyModel) {
        return "dateMidnight".equals(propertyModel.getDataType());
    }

    private void sizeColumns(BeanModel model) {
        List<String> propertyNames = model.getPropertyNames();
        for (int i = 0; i < propertyNames.size(); i++) {
            PropertyModel propertyModel = model.get(propertyNames.get(i));
            if (isDate(propertyModel)) {
                sheet.setColumnWidth(i, 16 * 256);
            } else {
                sheet.autoSizeColumn(i);
            }
        }
    }

    private CellStyle createHeaderStyle() {
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setFont(headerFont);
        return headerStyle;
    }

}
