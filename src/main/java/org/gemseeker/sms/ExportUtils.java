package org.gemseeker.sms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.gemseeker.sms.data.DailySummary;
import org.gemseeker.sms.data.Expense;
import org.gemseeker.sms.data.Payment;
import org.gemseeker.sms.data.Revenue;
import org.gemseeker.sms.views.ViewUtils;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
            header.setLeft("Revenues as of " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm:s a")));

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
            header.setLeft("Expenses as of " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm:s a")));

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

    public static void exportSummaries(ObservableList<DailySummary> summaries, File outputFile) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Daily Summary");

            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle rowStyle = createRowStyle(workbook);

            // Header
            Header header = sheet.getHeader();
            header.setLeft("Daily Summaries as of " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm:s a")));

            // Create Row Headers
            ObservableList<String> headers = FXCollections.observableArrayList(
                    "Date", "Forwarded", "Revenues", "Expenses", "Cash Balance"
            );
            Row headerRow = sheet.createRow(0);
            for (int c = 0; c < headers.size(); c++) {
                Cell cell = headerRow.createCell(c);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headers.get(c));
            }

            // Content
            for (int i = 1; i < summaries.size() + 1; i++) {
                Row row = sheet.createRow(i);
                DailySummary summary = summaries.get(i - 1);

                Cell c0 = row.createCell(0);
                c0.setCellStyle(rowStyle);
                c0.setCellValue(summary.getDate());

                Cell c1 = row.createCell(1);
                c1.setCellStyle(rowStyle);
                c1.setCellValue(ViewUtils.toStringMoneyFormat(summary.getForwarded()));

                Cell c2 = row.createCell(2);
                c2.setCellStyle(rowStyle);
                c2.setCellValue(ViewUtils.toStringMoneyFormat(summary.getRevenues()));

                Cell c3 = row.createCell(3);
                c3.setCellStyle(rowStyle);
                c3.setCellValue(ViewUtils.toStringMoneyFormat(summary.getExpenses()));

                Cell c4 = row.createCell(4);
                c4.setCellStyle(rowStyle);
                c4.setCellValue(ViewUtils.toStringMoneyFormat(summary.getBalance()));
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

    public static void exportPayments(ObservableList<Payment> payments, File outputFile) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Payments");

            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle rowStyle = createRowStyle(workbook);

            // Header
            Header header = sheet.getHeader();
            header.setLeft("Payments as of " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm:s a")));

            // Create Row Headers
            ObservableList<String> headers = FXCollections.observableArrayList(
                    "OR #", "Payment For", "Status", "Date", "Name", "To Pay", "Discount", "VAT", "Surcharges", "Total",
                    "Paid", "Balance", "Prepared By"
            );
            Row headerRow = sheet.createRow(0);
            for (int c = 0; c < headers.size(); c++) {
                Cell cell = headerRow.createCell(c);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headers.get(c));
            }

            // Content
            for (int i = 1; i < payments.size() + 1; i++) {
                Row row = sheet.createRow(i);
                Payment payment = payments.get(i);
                // OR #
                Cell c0 = row.createCell(0);
                c0.setCellStyle(rowStyle);
                c0.setCellValue(payment.getPaymentNo());

                // Payment For
                Cell c1 = row.createCell(1);
                c1.setCellStyle(rowStyle);
                c1.setCellValue(payment.getPaymentFor());

                // Status
                Cell c2 = row.createCell(2);
                c2.setCellStyle(rowStyle);
                c2.setCellValue(payment.getStatus());

                // Date
                Cell c3 = row.createCell(3);
                c3.setCellStyle(rowStyle);
                c3.setCellValue(payment.getPaymentDate());

                // Name
                Cell c4 = row.createCell(4);
                c4.setCellStyle(rowStyle);
                c4.setCellValue(payment.getName());

                // To Pay
                Cell c5 = row.createCell(5);
                c5.setCellStyle(rowStyle);
                c5.setCellValue(payment.getAmountToPay());

                // Discount (Actual amount)
                Cell c6 = row.createCell(6);
                c6.setCellStyle(rowStyle);
                c6.setCellValue(payment.getDiscount());

                // VAT
                Cell c7 = row.createCell(7);
                c7.setCellStyle(rowStyle);
                c7.setCellValue(payment.getVat());

                // Surcharges
                Cell c8 = row.createCell(8);
                c8.setCellStyle(rowStyle);
                c8.setCellValue(payment.getSurcharges());

                // Total
                Cell c9 = row.createCell(9);
                c9.setCellStyle(rowStyle);
                c9.setCellValue(payment.getAmountTotal());

                // Amount Paid
                Cell c10 = row.createCell(10);
                c10.setCellStyle(rowStyle);
                c10.setCellValue(payment.getAmountPaid());

                // Balance
                Cell c11 = row.createCell(11);
                c11.setCellStyle(rowStyle);
                c11.setCellValue(payment.getBalance());

                // Prepared by
                Cell c12 = row.createCell(12);
                c12.setCellStyle(rowStyle);
                c12.setCellValue(payment.getPreparedBy());
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
