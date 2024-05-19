package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.DataPlan;
import org.gemseeker.sms.data.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DataPlanController implements ModelController<DataPlan> {

    private final Database database;

    public DataPlanController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(DataPlan model) throws SQLException {
        String sql = String.format("INSERT INTO data_plans (name, speed, monthly_fee, tag, date_created, date_updated) " +
                "VALUES ('%s', '%d', '%f', '%s', '%s', '%s')", model.getName(), model.getSpeed(), model.getMonthlyFee(),
                model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(DataPlan model) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        String sql = String.format("UPDATE data_plans SET name='%s', speed='%d', monthly_fee='%f', tag='%s', " +
                "date_updated='%s' WHERE id='%d'", model.getName(), model.getSpeed(), model.getMonthlyFee(), model.getTag(),
                now, model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        String sql = String.format("UPDATE data_plans SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, now, id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public DataPlan get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM data_plans WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<DataPlan> getAll() throws SQLException {
        ObservableList<DataPlan> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM data_plans WHERE date_deleted IS NULL";
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private DataPlan fetchInfo(ResultSet rs) throws SQLException {
        DataPlan plan = new DataPlan();
        plan.setId(rs.getInt(1));
        plan.setName(rs.getString(2));
        plan.setSpeed(rs.getInt(3));
        plan.setMonthlyFee(rs.getDouble(4));
        plan.setTag(rs.getString(5));
        plan.setDateCreated(rs.getTimestamp(6).toLocalDateTime());
        plan.setDateUpdated(rs.getTimestamp(7).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(8);
        if (dateDeleted != null) plan.setDateDeleted(dateDeleted.toLocalDateTime());
        return plan;
    }
}
