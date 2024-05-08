package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PaymentController implements ModelController<Payment> {

    private final Database database;

    public PaymentController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Payment model) throws SQLException {
        String sql = String.format("INSERT INTO payments (payment_no, name, payment_for, extra_info, amount_to_pay, " +
                "discount, vat, surcharges, amount_total, amount_paid, balance, payment_date, prepared_by, " +
                "status, tag, date_created, date_updated) VALUES ('%s', '%s', '%s', '%s', '%f', '%f', '%f', '%f', '%f', '%f', " +
                "'%f', '%s', '%s', '%s', '%s', '%s', '%s')", model.getPaymentNo(), model.getName(), model.getPaymentFor(),
                model.getExtraInfo(), model.getAmountToPay(), model.getDiscount(), model.getVat(), model.getSurcharges(),
                model.getAmountTotal(), model.getAmountPaid(), model.getBalance(), model.getPaymentDate(),
                model.getPreparedBy(), model.getStatus(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Payment model) throws SQLException {
        // NOTE: Payment should only update the following: name; prepared_by,
        // payment_date, status, tag, and date_updated.
        String sql = String.format("UPDATE payments SET name='%s', payment_date='%s', prepared_by='%s', " +
                        "status='%s', tag='%s', date_updated='%s' WHERE id='%d'", model.getName(),
                model.getPaymentDate(), model.getPreparedBy(), model.getStatus(), model.getTag(),
                LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE payments SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public Payment get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM payments WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    public Payment getByPaymentNo(String paymentNo) throws SQLException {
        String sql = String.format("SELECT * FROM payments WHERE payment_no='%s' AND date_deleted IS NULL LIMIT 1", paymentNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    public boolean hasPayment(String paymentNo) throws SQLException {
        String sql = String.format("SELECT COUNT(*) FROM payments WHERE payment_no='%s'", paymentNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }

    public boolean hasPaymentForBilling(String billingNo) throws SQLException {
        String sql = String.format("SELECT COUNT(*) FROM payments WHERE extra_info='%s'", billingNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }

    /**
     * extraInfo can be billing_no, service_no, or product_no
     */
    public Payment getByExtraInfo(String info) throws SQLException {
        String sql = String.format("SELECT * FROM payments WHERE extra_info='%s' LIMIT 1", info);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM payments WHERE date_deleted IS NULL";
        ObservableList<Payment> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Payment fetchInfo(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt(1));
        payment.setPaymentNo(rs.getString(2));
        payment.setName(rs.getString(3));
        payment.setPaymentFor(rs.getString(4));
        payment.setExtraInfo(rs.getString(5));
        payment.setAmountToPay(rs.getDouble(6));
        payment.setDiscount(rs.getDouble(7));
        payment.setVat(rs.getDouble(8));
        payment.setSurcharges(rs.getDouble(9));
        payment.setAmountTotal(rs.getDouble(10));
        payment.setAmountPaid(rs.getDouble(11));
        payment.setBalance(rs.getDouble(12));
        payment.setPaymentDate(rs.getDate(13).toLocalDate());
        payment.setPreparedBy(rs.getString(14));
        payment.setStatus(rs.getString(15));
        payment.setTag(rs.getString(16));
        payment.setDateCreated(rs.getTimestamp(17).toLocalDateTime());
        payment.setDateUpdated(rs.getTimestamp(18).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(19);
        if (dateDeleted != null) payment.setDateDeleted(dateDeleted.toLocalDateTime());
        return payment;
    }

}
