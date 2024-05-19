package org.gemseeker.sms.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.gemseeker.sms.data.Expense;
import org.gemseeker.sms.data.Revenue;

import javax.swing.text.View;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExportUtils {

    private static CellStyle createHeaderStyle(Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderTop(BorderStyle.DASHED);
        headerStyle.setBorderLeft(BorderStyle.DASHED);
        headerStyle.setBorderRight(BorderStyle.DASHED);
        headerStyle.setBorderBottom(BorderStyle.DASHED);
        headerStyle.setFillBackgroundColor(IndexedColors.AQUA.index);
        headerStyle.setFont(headerFont);
        return headerStyle;
    }

    private static CellStyle createRowStyle(Workbook workbook) {
        CellStyle rowStyle = workbook.createCellStyle();
        rowStyle.setBorderTop(BorderStyle.DASHED);
        rowStyle.setBorderLeft(BorderStyle.DASHED);
        rowStyle.setBorderRight(BorderStyle.DASHED);
        rowStyle.setBorderBottom(BorderStyle.DASHED);
        rowStyle.setWrapText(true);
        return rowStyle;
    }

    public static void exportRevenues(ObservableList<Revenue> revenues, File outputFile) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Revenues");

            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle rowStyle = createRowStyle(workbook);

            // Header
            Header header = sheet.getHeader();
            header.setLeft("Date Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm:s a")));

            // Create Row Headers
            ObservableList<String> headers = FXCollections.observableArrayList(
                    "Type", "Description", "Amount", "Date"
            );
            Row headerRow = sheet.createRow(0);
            for (int c = 0; c < headers.size(); c++) {
                Cell cell = headerRow.createCell(c);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headers.get(c));
            }

            // Content
            for (int r = 1; r < revenues.size() + 1; r ++) {
                Row row = sheet.createRow(r);
                Revenue revenue = revenues.get(r - 1);
                Cell c0 = row.createCell(0);
                c0.setCellStyle(rowStyle);
                c0.setCellValue(revenue.getType());

                Cell c1 = row.createCell(1);
                c1.setCellStyle(rowStyle);
                c1.setCellValue(revenue.getDescription());

                Cell c2 = row.createCell(2);
                c2.setCellStyle(rowStyle);
                c2.setCellValue(ViewUtils.toStringMoneyFormat(revenue.getAmount()));
                Cell c3 = row.createCell(3);
                c3.setCellStyle(rowStyle);
                c3.setCellValue(revenue.getDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
            }

            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fileOutputStream);
        }

        if (outputFile.exists()) {
            Desktop.getDesktop().open(outputFile);
        }
    }

    public static void exportExpenses(ObservableList<Expense> expenses, File outputFile) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Expenses");

            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle rowStyle = createRowStyle(workbook);

            // Header
            Header header = sheet.getHeader();
            header.setLeft("Date Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm:s a")));

            // Create Row Headers
            ObservableList<String> headers = FXCollections.observableArrayList(
                    "Type", "Description", "Amount", "Date"
            );
            Row headerRow = sheet.createRow(0);
            for (int c = 0; c < headers.size(); c++) {
                Cell cell = headerRow.createCell(c);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headers.get(c));
            }

            // Content
            for (int exp = 1; exp < expenses.size() + 1; exp ++) {
                Row row = sheet.createRow(exp);
                Expense expense = expenses.get(exp - 1);
                Cell c0 = row.createCell(0);
                c0.setCellStyle(rowStyle);
                c0.setCellValue(expense.getType());

                Cell c1 = row.createCell(1);
                c1.setCellStyle(rowStyle);
                c1.setCellValue(expense.getDescription());

                Cell c2 = row.createCell(2);
                c2.setCellStyle(rowStyle);
                c2.setCellValue(ViewUtils.toStringMoneyFormat(expense.getAmount()));
                Cell c3 = row.createCell(3);
                c3.setCellStyle(rowStyle);
                c3.setCellValue(expense.getDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
            }

            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fileOutputStream);
        }

        if (outputFile.exists()) {
            Desktop.getDesktop().open(outputFile);
        }
    }

}
