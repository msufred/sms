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
        String sql = String.format("INSERT INTO payments (payment_no, name, payment_for, extra_info, prev_balance, " +
                        " amount_to_pay, discount, vat, surcharges, amount_total, amount_paid, balance, payment_date, " +
                        "prepared_by, status, tag, date_created, date_updated) VALUES " +
                        "('%s', '%s', '%s', '%s', '%f', '%f', '%f', '%f', '%f', '%f', '%f', '%f', '%s', '%s', '%s', " +
                        "'%s', '%s', '%s')", model.getPaymentNo(), model.getName(), model.getPaymentFor(),
                model.getExtraInfo(), model.getPrevBalance(), model.getAmountToPay(), model.getDiscount(),
                model.getVat(), model.getSurcharges(), model.getAmountTotal(), model.getAmountPaid(),
                model.getBalance(), model.getPaymentDate(), model.getPreparedBy(), model.getStatus(), model.getTag(),
                model.getDateCreated(), model.getDateUpdated());
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
        int index = 1;
        Payment payment = new Payment();
        payment.setId(rs.getInt(index++));
        payment.setPaymentNo(rs.getString(index++));
        payment.setName(rs.getString(index++));
        payment.setPaymentFor(rs.getString(index++));
        payment.setExtraInfo(rs.getString(index++));
        payment.setPrevBalance(rs.getDouble(index++));
        payment.setAmountToPay(rs.getDouble(index++));
        payment.setDiscount(rs.getDouble(index++));
        payment.setVat(rs.getDouble(index++));
        payment.setSurcharges(rs.getDouble(index++));
        payment.setAmountTotal(rs.getDouble(index++));
        payment.setAmountPaid(rs.getDouble(index++));
        payment.setBalance(rs.getDouble(index++));
        payment.setPaymentDate(rs.getDate(index++).toLocalDate());
        payment.setPreparedBy(rs.getString(index++));
        payment.setStatus(rs.getString(index++));
        payment.setTag(rs.getString(index++));
        payment.setDateCreated(rs.getTimestamp(index++).toLocalDateTime());
        payment.setDateUpdated(rs.getTimestamp(index++).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(index);
        if (dateDeleted != null) payment.setDateDeleted(dateDeleted.toLocalDateTime());
        return payment;
    }

}
