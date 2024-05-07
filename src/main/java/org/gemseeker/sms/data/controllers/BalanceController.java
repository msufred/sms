package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Balance;
import org.gemseeker.sms.data.Database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BalanceController implements ModelController<Balance> {

    private final Database database;

    public BalanceController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Balance model) throws SQLException {
        // NOTE! I always assume newly created Balance entry to have a Pending status. So I will not include the date_paid
        // value in the INSERT sql.
        String sql = String.format("INSERT INTO balances (account_no, amount, status, tag, date_created, date_updated) " +
                "VALUES ('%s', '%f', '%s', '%s', '%s', '%s')", model.getAccountNo(), model.getAmount(), model.getStatus(),
                model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Balance model) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("UPDATE balances SET amount='%f', status='%s', ", model.getAmount(), model.getStatus()));
        if (model.getDatePaid() != null) {
            sb.append(String.format("date_paid='%s', ", model.getDatePaid()));
        }
        sb.append(String.format("tag='%s', date_updated='%s' WHERE id='%d'",
                model.getTag(), LocalDateTime.now(), model.getId()));
        return database.executeQuery(sb.toString());
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE balances SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public Balance get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM balances WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<Balance> getAll() throws SQLException {
        String sql = "SELECT * FROM balances WHERE date_deleted IS NULL";
        ObservableList<Balance> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    public ObservableList<Balance> getUnpaidBalance(String accountNo) throws SQLException {
        String sql = String.format("SELECT * FROM balances WHERE account_no='%s' AND status='%s' AND date_deleted IS" +
                " NULL", accountNo, Balance.STATUS_PENDING);
        ObservableList<Balance> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Balance fetchInfo(ResultSet rs) throws SQLException {
        Balance balance = new Balance();
        balance.setId(rs.getInt(1));
        balance.setAccountNo(rs.getString(2));
        balance.setAmount(rs.getDouble(3));
        balance.setStatus(rs.getString(4));
        Date datePaid = rs.getDate(5);
        if (datePaid != null) balance.setDatePaid(datePaid.toLocalDate());
        balance.setTag(rs.getString(6));
        balance.setDateCreated(rs.getTimestamp(7).toLocalDateTime());
        balance.setDateUpdated(rs.getTimestamp(8).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(9);
        if (dateDeleted != null) {
            balance.setDateDeleted(dateDeleted.toLocalDateTime());
        }
        return balance;
    }
}
