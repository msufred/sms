package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Tower;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TowerController implements ModelController<Tower> {

    private final Database database;

    public TowerController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Tower model) throws SQLException {
        String sql = String.format("INSERT INTO towers (account_no, type, latitude, longitude, elevation, tower_height, " +
                "ip_address, parent_tower_id, status, tag, date_created, date_updated) VALUES ('%s', '%s', '%f', '%f', " +
                "'%f', '%f', '%s', '%d', '%s', '%s', '%s', '%s')", model.getAccountNo(), model.getType(), model.getLatitude(),
                model.getLongitude(), model.getElevation(), model.getTowerHeight(), model.getIpAddress(), model.getParentTowerId(),
                model.getStatus(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Tower model) throws SQLException {
        String sql = String.format("UPDATE towers SET type='%s', latitude='%f', longitude='%f', elevation='%f', tower_height='%f', " +
                "ip_address='%s', parent_tower_id='%d', status='%s', tag='%s', date_updated='%s' WHERE id='%d'",
                model.getType(), model.getLatitude(), model.getLongitude(), model.getElevation(), model.getTowerHeight(),
                model.getIpAddress(), model.getParentTowerId(), model.getStatus(), model.getTag(), LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE towers SET %s='%s', date_updated='%s' WHERE id='%d'", col, value,
                LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public Tower get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM towers WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<Tower> getAll() throws SQLException {
        ObservableList<Tower> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM towers WHERE date_deleted IS NULL";
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Tower fetchInfo(ResultSet rs) throws SQLException {
        Tower tower = new Tower();
        tower.setId(rs.getInt(1));
        tower.setAccountNo(rs.getString(2));
        tower.setType(rs.getString(3));
        tower.setLatitude(rs.getFloat(4));
        tower.setLongitude(rs.getFloat(5));
        tower.setElevation(rs.getFloat(6));
        tower.setTowerHeight(rs.getDouble(7));
        tower.setIpAddress(rs.getString(8));
        tower.setParentTowerId(rs.getInt(9));
        tower.setStatus(rs.getString(10));
        tower.setTag(rs.getString(11));
        tower.setDateCreated(rs.getTimestamp(12).toLocalDateTime());
        tower.setDateUpdated(rs.getTimestamp(13).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(14);
        if (dateDeleted != null) tower.setDateDeleted(dateDeleted.toLocalDateTime());
        return tower;
    }
}
