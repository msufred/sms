package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.BillingStatement;
import org.gemseeker.sms.data.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BillingStatementController implements ModelController<BillingStatement> {

    private final Database database;

    public BillingStatementController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(BillingStatement model) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO billing_statements (billing_no, include_balance, prev_balance, " +
                "monthly_fee, discount, penalty, vat, total, prepared_by, designation, received_by, " +
                "tag, date_created, ");
        if (model.getDatePrinted() != null) {
            sb.append("date_printed, ");
        }
        sb.append("date_updated) VALUES (");
        sb.append(String.format("'%s', '%s', '%f', '%f', '%f', '%f', '%f', '%f', '%s', '%s', '%s', " +
                "'%s', '%s', ", model.getBillingNo(), model.isIncludeBalance(), model.getPrevBalance(),
                model.getMonthlyFee(), model.getDiscount(), model.getPenalty(), model.getVat(),
                model.getTotal(), model.getPreparedBy(), model.getDesignation(), model.getReceivedBy(),
                model.getTag(), model.getDateCreated()));
        if (model.getDatePrinted() != null) {
            sb.append(String.format("'%s', ", model.getDatePrinted()));
        }
        sb.append(String.format("'%s')", model.getDateUpdated()));
        return database.executeQuery(sb.toString());
    }

    @Override
    public boolean update(BillingStatement model) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("UPDATE billing_statements SET include_balance='%s', " +
                "prev_balance='%f', monthly_fee='%f', discount='%f', penalty='%f', " +
                "vat='%f', total='%f', prepared_by='%s', designation='%s', received_by='%s', " +
                "tag='%s', ", model.isIncludeBalance(), model.getPrevBalance(),
                model.getMonthlyFee(), model.getDiscount(), model.getPenalty(),
                model.getVat(), model.getTotal(), model.getPreparedBy(), model.getDesignation(),
                model.getReceivedBy(), model.getTag()));
        if (model.getDatePrinted() != null) {
            sb.append(String.format("date_printed='%s', ", model.getDatePrinted()));
        }
        sb.append(String.format("date_updated='%s' WHERE id='%d'", LocalDateTime.now(),
                model.getId()));
        return database.executeQuery(sb.toString());
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE billing_statements SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public BillingStatement get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM billing_statements WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    public BillingStatement getByBillingNo(String billingNo) throws SQLException {
        String sql = String.format("SELECT * FROM billing_statements WHERE billing_no='%s' AND date_deleted IS NULL LIMIT 1", billingNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    public boolean hasBillingStatement(String billingNo) throws SQLException {
        String sql = String.format("SELECT COUNT(*) FROM billing_statements WHERE billing_no='%s' AND date_deleted IS NULL LIMIT 1", billingNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }

    @Override
    public ObservableList<BillingStatement> getAll() throws SQLException {
        String sql = "SELECT * FROM billing_statements WHERE date_deleted IS NULL";
        ObservableList<BillingStatement> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private BillingStatement fetchInfo(ResultSet rs) throws SQLException {
        BillingStatement b = new BillingStatement();
        b.setId(rs.getInt(1));
        b.setBillingNo(rs.getString(2));
        b.setIncludeBalance(rs.getBoolean(3));
        b.setPrevBalance(rs.getDouble(4));
        b.setMonthlyFee(rs.getDouble(5));
        b.setDiscount(rs.getDouble(6));
        b.setPenalty(rs.getDouble(7));
        b.setVat(rs.getDouble(8));
        b.setTotal(rs.getDouble(9));
        b.setPreparedBy(rs.getString(10));
        b.setDesignation(rs.getString(11));
        b.setReceivedBy(rs.getString(12));
        b.setTag(rs.getString(13));
        b.setDateCreated(rs.getTimestamp(14).toLocalDateTime());
        Timestamp datePrinted = rs.getTimestamp(15);
        if (datePrinted != null) b.setDatePrinted(datePrinted.toLocalDateTime());
        b.setDateUpdated(rs.getTimestamp(16).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(17);
        if (dateDeleted != null) b.setDateDeleted(dateDeleted.toLocalDateTime());
        return b;
    }
}
