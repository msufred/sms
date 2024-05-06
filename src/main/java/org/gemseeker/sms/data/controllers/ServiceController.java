package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ServiceController implements ModelController<Service> {

    private final Database database;

    public ServiceController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Service model) throws SQLException {
        String sql = String.format("INSERT INTO services (name, price, description, tag, date_created, date_updated) " +
                "VALUES ('%s', '%f', '%s', '%s', '%s', '%s')", model.getName(), model.getPrice(), model.getDescription(),
                model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Service model) throws SQLException {
        String sql = String.format("UPDATE services SET name='%s', price='%f', description='%s', tag='%s', " +
                "date_updated='%s' WHERE id='%d'", model.getName(), model.getPrice(), model.getDescription(),
                model.getTag(), LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE services SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public Service get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM services WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<Service> getAll() throws SQLException {
        String sql = "SELECT * FROM services WHERE date_deleted IS NULL";
        ObservableList<Service> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Service fetchInfo(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setId(rs.getInt(1));
        service.setName(rs.getString(2));
        service.setPrice(rs.getDouble(3));
        service.setDescription(rs.getString(4));
        service.setTag(rs.getString(5));
        service.setDateCreated(rs.getTimestamp(6).toLocalDateTime());
        service.setDateUpdated(rs.getTimestamp(7).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(8);
        if (dateDeleted != null) service.setDateDeleted(dateDeleted.toLocalDateTime());
        return service;
    }
}
