package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.CashTransaction;
import org.gemseeker.sms.data.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CashTransactionController implements ModelController<CashTransaction> {

    private final Database database;

    public CashTransactionController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(CashTransaction model) throws SQLException {
        String sql = String.format("INSERT INTO cash_transactions (type, description, amount, mode, ref, date, attachment, tag, " +
                "date_created, date_updated) VALUES ('%s', '%s', '%f', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                model.getType(), model.getDescription(), model.getAmount(), model.getMode(), model.getReference(),
                model.getDate(), model.getAttachment(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(CashTransaction model) throws SQLException {
        String sql = String.format("UPDATE cash_transactions SET type='%s', description='%s', amount='%f', mode='%s', " +
                "ref='%s', date='%s', attachment='%s', tag='%s', date_updated='%s' WHERE id='%d'", model.getType(),
                model.getDescription(), model.getAmount(), model.getMode(), model.getReference(), model.getDate(),
                model.getAttachment(), model.getTag(), LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE cash_transactions SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public CashTransaction get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM cash_transactions WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) {
                return fetchInfo(rs);
            }
        }
        return null;
    }

    @Override
    public ObservableList<CashTransaction> getAll() throws SQLException {
        ObservableList<CashTransaction> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult("SELECT * FROM cash_transactions WHERE date_deleted IS NULL")) {
            while (rs.next()) {
                list.add(fetchInfo(rs));
            }
        }
        return list;
    }

    public ObservableList<CashTransaction> getCashTransactionsToday() throws SQLException {
        String sql = String.format("SELECT * FROM cash_transactions WHERE date='%s' AND date_deleted IS NULL",
                LocalDate.now());
        ObservableList<CashTransaction> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) {
                list.add(fetchInfo(rs));
            }
        }
        return list;
    }

    public ObservableList<CashTransaction> getByDate(LocalDate date) throws SQLException {
        String sql = String.format("SELECT * FROM cash_transactions WHERE date='%s' AND date_deleted IS NULL", date);
        ObservableList<CashTransaction> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) {
                list.add(fetchInfo(rs));
            }
        }
        return list;
    }

    private CashTransaction fetchInfo(ResultSet rs) throws SQLException {
        int index = 1;
        CashTransaction ct = new CashTransaction();
        ct.setId(rs.getInt(index++));
        ct.setType(rs.getString(index++));
        ct.setDescription(rs.getString(index++));
        ct.setAmount(rs.getDouble(index++));
        ct.setMode(rs.getString(index++));
        ct.setReference(rs.getString(index++));
        ct.setDate(rs.getDate(index++).toLocalDate());
        ct.setAttachment(rs.getString(index++));
        ct.setTag(rs.getString(index++));
        ct.setDateCreated(rs.getTimestamp(index++).toLocalDateTime());
        ct.setDateUpdated(rs.getTimestamp(index++).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(index);
        if (dateDeleted != null) {
            ct.setDateDeleted(dateDeleted.toLocalDateTime());
        }
        return ct;
    }
}
