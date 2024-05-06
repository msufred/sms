package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SubscriptionController implements ModelController<Subscription> {

    private final Database database;

    public SubscriptionController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Subscription model) throws SQLException {
        String sql = String.format("INSERT INTO subscriptions (account_no, plan_type, speed, monthly_fee, ip_address, " +
                "start_date, end_date, status, tag, date_created, date_updated) VALUES ('%s', '%s', '%d', '%f', '%s', " +
                "'%s', '%s', '%s', '%s', '%s', '%s')", model.getAccountNo(), model.getPlanType(), model.getSpeed(),
                model.getMonthlyFee(), model.getIpAddress(), model.getStartDate(), model.getEndDate(), model.getStatus(),
                model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Subscription model) throws SQLException {
        String sql = String.format("UPDATE subscriptions SET plan_type='%s', speed='%d', monthly_fee='%f', " +
                "ip_address='%s', start_date='%s', end_date='%s', status='%s', tag='%s', date_updated='%s' WHERE id='%d'",
                model.getPlanType(), model.getSpeed(), model.getMonthlyFee(), model.getIpAddress(), model.getStartDate(),
                model.getEndDate(), model.getStatus(), model.getTag(), LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE subsriptions SET %s='%s', date_updated='%s' WHERE id='%d'", col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public Subscription get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM subscriptions WHERE id='%d' AND date_deleted IS NULL", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    public Subscription getByAccountNo(String accountNo) throws SQLException {
        String sql = String.format("SELECT * FROM subscriptions WHERE account_no='%s' AND date_deleted IS NULL", accountNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<Subscription> getAll() throws SQLException {
        ObservableList<Subscription> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM subscriptions WHERE date_deleted IS NULL";
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Subscription fetchInfo(ResultSet rs) throws SQLException {
        Subscription sub = new Subscription();
        sub.setId(rs.getInt(1));
        sub.setAccountNo(rs.getString(2));
        sub.setPlanType(rs.getString(3));
        sub.setSpeed(rs.getInt(4));
        sub.setMonthlyFee(rs.getDouble(5));
        sub.setIpAddress(rs.getString(6));
        sub.setStartDate(rs.getDate(7).toLocalDate());
        sub.setEndDate(rs.getDate(8).toLocalDate());
        sub.setStatus(rs.getString(9));
        sub.setTag(rs.getString(10));
        sub.setDateCreated(rs.getTimestamp(11).toLocalDateTime());
        sub.setDateUpdated(rs.getTimestamp(12).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(13);
        if (dateDeleted != null) sub.setDateDeleted(dateDeleted.toLocalDateTime());
        return sub;
    }
}
