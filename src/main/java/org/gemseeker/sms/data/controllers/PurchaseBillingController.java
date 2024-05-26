package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Billing;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.PurchaseBilling;
import org.gemseeker.sms.data.controllers.models.PurchasePayment;

import java.sql.*;
import java.time.LocalDateTime;

public class PurchaseBillingController implements ModelController<PurchaseBilling> {

    private final Database database;

    public PurchaseBillingController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(PurchaseBilling model) throws SQLException {
        String sql = String.format("INSERT INTO purchase_billings (billing_no, name, walk_in, to_pay, status, payment_no, " +
                "tag, date_created, date_updated) VALUES ('%s', '%s', '%s', '%f', '%s', '%s', '%s', '%s', '%s')",
                model.getBillingNo(), model.getName(), model.isWalkInClient(), model.getToPay(), model.getStatus(),
                model.getPaymentNo(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(PurchaseBilling model) throws SQLException {
        String sql = String.format("UPDATE purchase_billings SET name='%s', walk_in='%s', to_pay='%f', status='%s', " +
                "payment_no='%s', tag='%s', date_updated='%s' WHERE id='%d'", model.getName(), model.isWalkInClient(),
                model.getToPay(), model.getStatus(), model.getPaymentNo(), model.getTag(), LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE purchase_billings SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public PurchaseBilling get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM purchase_billings WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    public PurchaseBilling getByBillingNo(String billingNo) throws SQLException {
        String sql = String.format("SELECT * FROM purchase_billings WHERE billing_no='%s' AND date_deleted IS NULL", billingNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    public boolean hasBilling(String billingNo) throws SQLException {
        String sql = String.format("SELECT COUNT(*) FROM purchase_billings WHERE billing_no='%s'", billingNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }

    @Override
    public ObservableList<PurchaseBilling> getAll() throws SQLException {
        String sql = "SELECT * FROM purchase_billings WHERE date_deleted IS NULL";
        ObservableList<PurchaseBilling> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private PurchaseBilling fetchInfo(ResultSet rs) throws SQLException {
        PurchaseBilling billing = new PurchaseBilling();
        billing.setId(rs.getInt(1));
        billing.setBillingNo(rs.getString(2));
        billing.setName(rs.getString(3));
        billing.setWalkInClient(rs.getBoolean(4));
        billing.setToPay(rs.getDouble(5));
        billing.setStatus(rs.getString(6));
        billing.setPaymentNo(rs.getString(7));
        billing.setTag(rs.getString(8));
        billing.setDateUpdated(rs.getTimestamp(9).toLocalDateTime());
        billing.setDateUpdated(rs.getTimestamp(10).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(11);
        if (dateDeleted != null) billing.setDateDeleted(dateDeleted.toLocalDateTime());
        return billing;
    }

    public ObservableList<PurchasePayment> getAllBillingWithPayment() throws SQLException {
        String sql = "SELECT " +
                "purchase_billings.id, " +
                "purchase_billings.billing_no, " +
                "purchase_billings.name, " +
                "purchase_billings.walk_in, " +
                "purchase_billings.to_pay, " +
                "purchase_billings.status, " +
                "purchase_billings.payment_no, " +
                "payments.amount_total, " +
                "payments.amount_paid, " +
                "payments.balance, " +
                "payments.payment_date, " +
                "FROM purchase_billings " +
                "LEFT OUTER JOIN payments ON payments.payment_no = purchase_billings.payment_no " +
                "WHERE purchase_billings.date_deleted IS NULL";
        ObservableList<PurchasePayment> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchPurchasePayment(rs));
        }
        return list;
    }

    private PurchasePayment fetchPurchasePayment(ResultSet rs) throws SQLException {
        PurchasePayment payment = new PurchasePayment();
        payment.setPurchaseId(rs.getInt(1));
        payment.setBillingNo(rs.getString(2));
        payment.setName(rs.getString(3));
        payment.setWalkIn(rs.getBoolean(4));
        payment.setToPay(rs.getDouble(5));
        payment.setPaymentNo(rs.getString(6));
        payment.setAmountTotal(rs.getDouble(7));
        payment.setAmountPaid(rs.getDouble(8));
        payment.setBalance(rs.getDouble(9));
        Date paymentDate = rs.getDate(10);
        if (paymentDate != null) payment.setPaymentDate(paymentDate.toLocalDate());
        return payment;
    }
}
