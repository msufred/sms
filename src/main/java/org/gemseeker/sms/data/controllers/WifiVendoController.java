package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.WifiVendo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class WifiVendoController implements ModelController<WifiVendo> {

    private final Database database;

    public WifiVendoController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(WifiVendo model) throws SQLException {
        String sql = String.format("INSERT INTO wifi_vendos (name, ip_address, status, tag, date_created, date_updated) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", model.getName(), model.getIpAddress(), model.getStatus(),
                model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(WifiVendo model) throws SQLException {
        String sql = String.format("UPDATE wifi_vendos SET name='%s', ip_address='%s', status='%s', tag='%s', " +
                "date_updated='%s' WHERE id='%d'", model.getName(), model.getIpAddress(), model.getStatus(), model.getTag(),
                LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE wifi_vendos SET %s='%s', date_updated='%s' WHERE id='%d'", col, value,
                LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public WifiVendo get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM wifi_vendos WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<WifiVendo> getAll() throws SQLException {
        String sql = "SELECT * FROM wifi_vendos WHERE date_deleted IS NULL";
        ObservableList<WifiVendo> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private WifiVendo fetchInfo(ResultSet rs) throws SQLException {
        WifiVendo vendo = new WifiVendo();
        vendo.setId(rs.getInt(1));
        vendo.setName(rs.getString(2));
        vendo.setIpAddress(rs.getString(3));
        vendo.setStatus(rs.getString(4));
        vendo.setTag(rs.getString(5));
        vendo.setDateCreated(rs.getTimestamp(6).toLocalDateTime());
        vendo.setDateUpdated(rs.getTimestamp(7).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(8);
        if (dateDeleted != null) vendo.setDateDeleted(dateDeleted.toLocalDateTime());
        return vendo;
    }
}
