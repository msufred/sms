package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Expense;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExpenseController implements ModelController<Expense> {

    private final Database database;

    public ExpenseController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Expense model) throws SQLException {
        String sql = String.format("INSERT INTO expenses (category, type, description, amount, mode, ref, date, attachment, tag, date_created, " +
                "date_updated) VALUES ('%s', '%s', '%s', '%f', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", model.getCategory(), model.getType(),
                model.getDescription(), model.getAmount(), model.getMode(), model.getRef(), model.getDate(), model.getAttachment(),
                model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Expense model) throws SQLException {
        String sql = String.format("UPDATE expenses SET category='%s', type='%s', description='%s', amount='%f', mode='%s', " +
                        "ref='%s', date='%s', attachment='%s', tag='%s', date_updated='%s' WHERE id='%d'", model.getCategory(),
                model.getType(), model.getDescription(), model.getAmount(), model.getMode(), model.getRef(), model.getDate(),
                model.getAttachment(), model.getTag(), LocalDateTime.now(), model.getId());
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

    public ObservableList<Expense> getExpensesToday() throws SQLException {
        String sql = String.format("SELECT * FROM expenses WHERE date='%s' AND date_deleted IS NULL",
                LocalDate.now());
        ObservableList<Expense> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    public ObservableList<Expense> getPrevExpenses() throws SQLException {
        String sql = String.format("SELECT * FROM expenses WHERE date='%s' AND date_deleted IS NULL",
                LocalDate.now().minusDays(1));
        ObservableList<Expense> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    public ObservableList<Expense> getByDate(LocalDate date) throws SQLException {
        String sql = String.format("SELECT * FROM expenses WHERE date='%s' AND date_deleted IS NULL", date);
        ObservableList<Expense> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Expense fetchInfo(ResultSet rs) throws SQLException {
        int index = 1;
        Expense expense = new Expense();
        expense.setId(rs.getInt(index++));
        expense.setCategory(rs.getString(index++));
        expense.setType(rs.getString(index++));
        expense.setDescription(rs.getString(index++));
        expense.setAmount(rs.getDouble(index++));
        expense.setMode(rs.getString(index++));
        expense.setRef(rs.getString(index++));
        expense.setDate(rs.getDate(index++).toLocalDate());
        expense.setAttachment(rs.getString(index++));
        expense.setTag(rs.getString(index++));
        expense.setDateCreated(rs.getTimestamp(index++).toLocalDateTime());
        expense.setDateUpdated(rs.getTimestamp(index++).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(index);
        if (dateDeleted != null) expense.setDateDeleted(dateDeleted.toLocalDateTime());
        return expense;
    }
}
