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
        String sql = String.format("INSERT INTO payment_items (payment_no, item_no, item_name, serial, amount, price, quantity, tag, " +
                "date_created, date_updated) VALUES ('%s', '%s', '%s', '%s', '%f', '%f', '%d', '%s', '%s', '%s')", model.getPaymentNo(),
                model.getItemNo(), model.getItemName(), model.getSerial(), model.getAmount(), model.getPrice(), model.getQuantity(),
                model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(PaymentItem model) throws SQLException {
        String sql = String.format("UPDATE payment_items SET item_no='%s', item_name='%s', serial='%s', amount='%f', " +
                "price='%f', quantity='%d', tag='%s', date_updated='%s' WHERE id='%d'", model.getItemNo(), model.getItemName(),
                model.getSerial(), model.getAmount(), model.getPrice(), model.getQuantity(), model.getTag(), LocalDateTime.now(),
                model.getId());
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

    public ObservableList<PaymentItem> getByPayment(String paymentNo) throws SQLException {
        String sql = "SELECT * FROM payment_items WHERE payment_no='" + paymentNo + "'";
        ObservableList<PaymentItem> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private PaymentItem fetchInfo(ResultSet rs) throws SQLException {
        int index = 1;
        PaymentItem item = new PaymentItem();
        item.setId(rs.getInt(index++));
        item.setPaymentNo(rs.getString(index++));
        item.setItemNo(rs.getString(index++));
        item.setItemName(rs.getString(index++));
        item.setSerial(rs.getString(index++));
        item.setAmount(rs.getDouble(index++));
        item.setPrice(rs.getDouble(index++));
        item.setQuantity(rs.getInt(index++));
        item.setTag(rs.getString(index++));
        item.setDateCreated(rs.getTimestamp(index++).toLocalDateTime());
        item.setDateUpdated(rs.getTimestamp(index++).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(index);
        if (dateDeleted != null) item.setDateDeleted(dateDeleted.toLocalDateTime());
        return item;
    }
}
