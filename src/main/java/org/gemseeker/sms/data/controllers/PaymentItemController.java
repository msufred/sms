package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.PaymentItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PaymentItemController implements ModelController<PaymentItem> {

    private final Database database;

    public PaymentItemController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(PaymentItem model) throws SQLException {
        String sql = String.format("INSERT INTO payment_items (payment_no, item_no, item_name, serial, amount, tag, " +
                "date_created, date_updated) VALUES ('%s', '%s', '%s', '%s', '%f', '%s', '%s', '%s')", model.getPaymentNo(),
                model.getItemNo(), model.getItemName(), model.getSerial(), model.getAmount(), model.getTag(), model.getDateCreated(),
                model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(PaymentItem model) throws SQLException {
        String sql = String.format("UPDATE payment_items SET item_no='%s', item_name='%s', serial='%s', amount='%f', " +
                "tag='%s', date_updated='%s' WHERE id='%d'", model.getItemNo(), model.getItemName(), model.getSerial(),
                model.getAmount(), model.getTag(), LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE payment_items SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public PaymentItem get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM payment_items WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<PaymentItem> getAll() throws SQLException {
        String sql = "SELECT * FROM payment_items WHERE date_deleted IS NULL";
        ObservableList<PaymentItem> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private PaymentItem fetchInfo(ResultSet rs) throws SQLException {
        PaymentItem item = new PaymentItem();
        item.setId(rs.getInt(1));
        item.setPaymentNo(rs.getString(2));
        item.setItemNo(rs.getString(3));
        item.setItemName(rs.getString(4));
        item.setSerial(rs.getString(5));
        item.setAmount(rs.getDouble(6));
        item.setTag(rs.getString(7));
        item.setDateCreated(rs.getTimestamp(8).toLocalDateTime());
        item.setDateUpdated(rs.getTimestamp(9).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(10);
        if (dateDeleted != null) item.setDateDeleted(dateDeleted.toLocalDateTime());
        return item;
    }
}
