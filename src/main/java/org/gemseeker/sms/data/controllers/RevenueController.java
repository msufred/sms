package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Revenue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RevenueController implements ModelController<Revenue> {

    private final Database database;

    public RevenueController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Revenue model) throws SQLException {
        String sql = String.format("INSERT INTO revenues (type, description, amount, mode, ref, date, attachment, tag, " +
                "date_created, date_updated) VALUES ('%s', '%s', '%f', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                model.getType(), model.getDescription(), model.getAmount(), model.getMode(), model.getReference(), model.getDate(),
                model.getAttachment(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Revenue model) throws SQLException {
        String sql = String.format("UPDATE revenues SET type='%s', description='%s', amount='%f', " +
                "date='%s', attachment='%s', tag='%s', date_updated='%s' WHERE id='%d'", model.getType(), model.getDescription(),
                model.getAmount(), model.getDate(), model.getAttachment(), model.getTag(), LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE revenues SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public Revenue get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM revenues WHERE id='%d' AND date_deleted IS NULL", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<Revenue> getAll() throws SQLException {
        String sql = "SELECT * FROM revenues WHERE date_deleted IS NULL";
        ObservableList<Revenue> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    public ObservableList<Revenue> getRevenuesToday() throws SQLException {
        String sql = String.format("SELECT * FROM revenues WHERE date='%s' AND date_deleted IS NULL",
                LocalDate.now());
        ObservableList<Revenue> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    public ObservableList<Revenue> getPrevRevenues() throws SQLException {
        String sql = String.format("SELECT * FROM revenues WHERE date='%s' AND date_deleted IS NULL",
                LocalDate.now().minusDays(1));
        ObservableList<Revenue> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    public ObservableList<Revenue> getByDate(LocalDate date) throws SQLException {
        String sql = String.format("SELECT * FROM revenues WHERE date='%s' AND date_deleted IS NULL", date);
        ObservableList<Revenue> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Revenue fetchInfo(ResultSet rs) throws SQLException {
        int index = 1;
        Revenue revenue = new Revenue();
        revenue.setId(rs.getInt(index++));
        revenue.setType(rs.getString(index++));
        revenue.setDescription(rs.getString(index++));
        revenue.setAmount(rs.getDouble(index++));
        revenue.setMode(rs.getString(index++));
        revenue.setReference(rs.getString(index++));
        revenue.setDate(rs.getDate(index++).toLocalDate());
        revenue.setAttachment(rs.getString(index++));
        revenue.setTag(rs.getString(index++));
        revenue.setDateCreated(rs.getTimestamp(index++).toLocalDateTime());
        revenue.setDateUpdated(rs.getTimestamp(index++).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(index);
        if (dateDeleted != null) revenue.setDateDeleted(dateDeleted.toLocalDateTime());
        return revenue;
    }
}
