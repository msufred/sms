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
        String sql = String.format("INSERT INTO towers (account_no, type, name, latitude, longitude, elevation, " +
                        "tower_height, ip_address, parent_tower_id, parent_name, status, tag, date_created, date_updated) VALUES " +
                        "('%s', '%s', '%s', '%f', '%f', '%f', '%f', '%s', '%d', '%s', '%s', '%s', '%s', '%s')",
                model.getAccountNo(), model.getType(), model.getName(), model.getLatitude(), model.getLongitude(),
                model.getElevation(), model.getTowerHeight(), model.getIpAddress(), model.getParentTowerId(),
                model.getParentName(), model.getStatus(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Tower model) throws SQLException {
        String sql = String.format("UPDATE towers SET type='%s', name='%s', latitude='%f', longitude='%f', " +
                        "elevation='%f', tower_height='%f', ip_address='%s', parent_tower_id='%d', parent_name='%s', " +
                        "status='%s', tag='%s', date_updated='%s' WHERE id='%d'",
                model.getType(), model.getName(), model.getLatitude(), model.getLongitude(), model.getElevation(),
                model.getTowerHeight(), model.getIpAddress(), model.getParentTowerId(), model.getParentName(),
                model.getStatus(), model.getTag(), LocalDateTime.now(), model.getId());
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

    public Tower getByAccountNo(String accountNo) throws SQLException {
        String sql = String.format("SELECT * FROM towers WHERE account_no='%s' AND date_deleted IS NULL LIMIT 1", accountNo);
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
        int index = 1;
        Tower tower = new Tower();
        tower.setId(rs.getInt(index++));
        tower.setAccountNo(rs.getString(index++));
        tower.setType(rs.getString(index++));
        tower.setName(rs.getString(index++));
        tower.setLatitude(rs.getFloat(index++));
        tower.setLongitude(rs.getFloat(index++));
        tower.setElevation(rs.getFloat(index++));
        tower.setTowerHeight(rs.getDouble(index++));
        tower.setIpAddress(rs.getString(index++));
        tower.setParentTowerId(rs.getInt(index++));
        tower.setParentName(rs.getString(index++));
        tower.setStatus(rs.getString(index++));
        tower.setTag(rs.getString(index++));
        tower.setDateCreated(rs.getTimestamp(index++).toLocalDateTime());
        tower.setDateUpdated(rs.getTimestamp(index++).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(index);
        if (dateDeleted != null) tower.setDateDeleted(dateDeleted.toLocalDateTime());
        return tower;
    }

    public boolean hasTower(String accountNo) throws SQLException {
        String sql = String.format("SELECT COUNT(*) FROM towers WHERE account_no='%s'", accountNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }

}
