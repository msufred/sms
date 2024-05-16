package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Expense;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ExpenseController implements ModelController<Expense> {

    private final Database database;

    public ExpenseController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Expense model) throws SQLException {
        String sql = String.format("INSERT INTO expenses (type, description, amount, date, tag, date_created, " +
                "date_updated) VALUES ('%s', '%s', '%f', '%s', '%s', '%s', '%s')", model.getType(), model.getDescription(),
                model.getAmount(), model.getDate(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Expense model) throws SQLException {
        String sql = String.format("UPDATE expenses SET type='%s', description='%s', amount='%f', date='%s', tag='%s', " +
                "date_updated='%s' WHERE id='%d'", model.getType(), model.getDescription(), model.getAmount(), model.getDate(),
                model.getTag(), LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE expenses SET %s='%s', date_updated='%s' WHERE id='%d'", col, value,
                LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public Expense get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM expenses WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<Expense> getAll() throws SQLException {
        String sql = "SELECT * FROM expenses WHERE date_deleted IS NULL";
        ObservableList<Expense> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Expense fetchInfo(ResultSet rs) throws SQLException {
        Expense expense = new Expense();
        expense.setId(rs.getInt(1));
        expense.setType(rs.getString(2));
        expense.setDescription(rs.getString(3));
        expense.setAmount(rs.getDouble(4));
        expense.setDate(rs.getDate(5).toLocalDate());
        expense.setTag(rs.getString(6));
        expense.setDateCreated(rs.getTimestamp(7).toLocalDateTime());
        expense.setDateUpdated(rs.getTimestamp(8).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(9);
        if (dateDeleted != null) expense.setDateDeleted(dateDeleted.toLocalDateTime());
        return expense;
    }
}
