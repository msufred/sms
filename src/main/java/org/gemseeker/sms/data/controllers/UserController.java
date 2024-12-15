package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserController implements ModelController<User> {

    private final Database database;

    public UserController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(User model) throws SQLException {
        String sql = String.format("INSERT INTO users (username, password, fullname, designation, role, tag, date_created, date_updated) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", model.getUsername(), model.getPassword(), model.getFullname(),
                model.getDesignation(), model.getRole(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    public long insertWithId(User model) throws SQLException {
        String sql = String.format("INSERT INTO users (username, password, fullname, designation, role, tag, date_created, date_updated) " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", model.getUsername(), model.getPassword(), model.getFullname(),
                model.getDesignation(), model.getRole(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQueryReturnId(sql);
    }

    @Override
    public boolean update(User model) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        String sql = String.format("UPDATE users SET username='%s', password='%s', fullname='%s', designation='%s', " +
                        "role='%s', tag='%s', date_updated='%s' WHERE id='%d'", model.getUsername(), model.getPassword(),
                model.getFullname(), model.getDesignation(), model.getRole(), model.getTag(), now, model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        String sql = String.format("UPDATE users SET %s='%s', date_updated='%s' WHERE id='%d'", col, value, now, id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public User get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM users WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    public User getUser(String username, String password) throws SQLException {
        String sql = String.format("SELECT * FROM users WHERE username='%s' AND password='%s' AND date_deleted IS NULL LIMIT 1",
                username, password);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<User> getAll() throws SQLException {
        ObservableList<User> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users WHERE date_deleted IS NULL";
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private User fetchInfo(ResultSet rs) throws SQLException {
        int index = 1;
        User user = new User();
        user.setId(rs.getInt(index++));
        user.setUsername(rs.getString(index++));
        user.setPassword(rs.getString(index++));
        user.setFullname(rs.getString(index++));
        user.setDesignation(rs.getString(index++));
        user.setRole(rs.getString(index++));
        user.setTag(rs.getString(index++));
        user.setDateCreated(rs.getTimestamp(index++).toLocalDateTime());
        user.setDateUpdated(rs.getTimestamp(index++).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(index);
        if (dateDeleted != null) user.setDateDeleted(dateDeleted.toLocalDateTime());
        return user;
    }

    public boolean hasUsers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE date_deleted IS NULL";
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }
}
