package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ScheduleController implements ModelController<Schedule> {

    private final Database database;

    public ScheduleController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Schedule model) throws SQLException {
        String sql = String.format("INSERT INTO schedules (title, description, date, status, tag, date_created, date_updated) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')", model.getTitle(), model.getDescription(), model.getDate(),
                model.getStatus(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Schedule model) throws SQLException {
        String sql = String.format("UPDATE schedules SET title='%s', description='%s', date='%s', status='%s', tag='%s', " +
                "date_updated='%s' WHERE id='%d'", model.getTitle(), model.getDescription(), model.getDate(), model.getStatus(),
                model.getTag(), LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE schedules SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public Schedule get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM schedules WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<Schedule> getAll() throws SQLException {
        String sql = "SELECT * FROM schedules WHERE date_deleted IS NULL";
        ObservableList<Schedule> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Schedule fetchInfo(ResultSet rs) throws SQLException {
        Schedule schedule = new Schedule();
        schedule.setId(rs.getInt(1));
        schedule.setTitle(rs.getString(2));
        schedule.setDescription(rs.getString(3));
        schedule.setDate(rs.getDate(4).toLocalDate());
        schedule.setStatus(rs.getString(5));
        schedule.setTag(rs.getString(6));
        schedule.setDateCreated(rs.getTimestamp(7).toLocalDateTime());
        schedule.setDateUpdated(rs.getTimestamp(8).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(9);
        if (dateDeleted != null) schedule.setDateDeleted(dateDeleted.toLocalDateTime());
        return schedule;
    }

    public int getOutdatedCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM schedules WHERE status='outdated'";
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int getUpcomingCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM schedules WHERE status='pending'";
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }
}
