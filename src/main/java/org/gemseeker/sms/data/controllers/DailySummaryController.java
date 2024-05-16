package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.DailySummary;
import org.gemseeker.sms.data.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DailySummaryController implements ModelController<DailySummary> {

    private final Database database;

    public DailySummaryController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(DailySummary model) throws SQLException {
        String sql = String.format("INSERT INTO daily_summaries (date, forwarded, revenues, expenses, " +
                "balance, tag, date_created, date_updated) VALUES ('%s', '%f', '%f', '%f', '%f', '%s', '%s', '%s')",
                model.getDate(), model.getForwarded(), model.getRevenues(), model.getExpenses(), model.getBalance(),
                model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(DailySummary model) throws SQLException {
        String sql = String.format("UPDATE daily_summaries SET date='%s', forwarded='%f', revenues='%f', " +
                "expenses='%f', balance='%f', tag='%s', date_updated='%s' WHERE id='%d'",
                model.getDate(), model.getForwarded(), model.getRevenues(), model.getExpenses(), model.getBalance(),
                model.getTag(), LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE daily_summaries SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public DailySummary get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM daily_summaries WHERE date_deleted IS NULL AND id='%d'", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<DailySummary> getAll() throws SQLException {
        String sql = "SELECT * FROM daily_summaries WHERE date_deleted IS NULL";
        ObservableList<DailySummary> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    public DailySummary getByDate(LocalDate date) throws SQLException {
        String sql = String.format("SELECT * FROM daily_summaries WHERE date_deleted IS NULL AND date='%s'", date);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    private DailySummary fetchInfo(ResultSet rs) throws SQLException {
        DailySummary dailySummary = new DailySummary();
        dailySummary.setId(rs.getInt(1));
        dailySummary.setDate(rs.getDate(2).toLocalDate());
        dailySummary.setForwarded(rs.getDouble(3));
        dailySummary.setRevenues(rs.getDouble(4));
        dailySummary.setExpenses(rs.getDouble(5));
        dailySummary.setBalance(rs.getDouble(6));
        dailySummary.setTag(rs.getString(7));
        dailySummary.setDateCreated(rs.getTimestamp(8).toLocalDateTime());
        dailySummary.setDateUpdated(rs.getTimestamp(9).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(10);
        if (dateDeleted != null) dailySummary.setDateDeleted(dateDeleted.toLocalDateTime());
        return dailySummary;
    }
}
