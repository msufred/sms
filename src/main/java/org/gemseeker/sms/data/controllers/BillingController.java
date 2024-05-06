package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Billing;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.models.BillingBillingPayment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BillingController implements ModelController<Billing> {

    private final Database database;

    public BillingController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Billing model) throws SQLException {
        String sql = String.format("INSERT INTO billings (billing_no, account_no, from_date, to_date, due_date, to_pay, " +
                "status, payment_no, tag, date_created, date_updated) VALUES ('%s', '%s', '%s', '%s', '%s', '%f', '%s', " +
                "'%s', '%s', '%s')", model.getBillingNo(), model.getAccountNo(), model.getFromDate(), model.getToDate(),
                model.getDueDate(), model.getToPay(), model.getStatus(), model.getPaymentNo(), model.getTag(),
                model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Billing model) throws SQLException {
        String sql = String.format("UPDATE billings SET from_date='%s', to_date='%s', due_date='%s', to_pay='%f', " +
                "status='%s', payment_no='%s', tag='%s', date_updated='%s' WHERE id='%d'", model.getFromDate(), model.getToDate(),
                model.getDueDate(), model.getToPay(), model.getStatus(), model.getPaymentNo(), model.getTag(), LocalDateTime.now(),
                model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE billings SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public Billing get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM billings WHERE id='%d' AND date_deleted IS NULL", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    public Billing getByBillingNo(String billingNo) throws SQLException {
        String sql = String.format("SELECT * FROM billings WHERE billing_no='%s' AND date_deleted IS NULL", billingNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    public boolean hasBilling(String billingNo) throws SQLException {
        String sql = String.format("SELECT COUNT(*) FROM billings WHERE billing_no='%s'", billingNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }

    @Override
    public ObservableList<Billing> getAll() throws SQLException {
        String sql = "SELECT * FROM billings WHERE date_deleted IS NULL";
        ObservableList<Billing> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Billing fetchInfo(ResultSet rs) throws SQLException {
        Billing billing = new Billing();
        billing.setId(rs.getInt(1));
        billing.setBillingNo(rs.getString(2));
        billing.setAccountNo(rs.getString(3));
        billing.setFromDate(rs.getDate(4).toLocalDate());
        billing.setToDate(rs.getDate(5).toLocalDate());
        billing.setDueDate(rs.getDate(6).toLocalDate());
        billing.setToPay(rs.getDouble(7));
        billing.setStatus(rs.getString(8));
        billing.setPaymentNo(rs.getString(9));
        billing.setTag(rs.getString(10));
        billing.setDateCreated(rs.getTimestamp(11).toLocalDateTime());
        billing.setDateUpdated(rs.getTimestamp(12).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(13);
        if (dateDeleted != null) billing.setDateDeleted(dateDeleted.toLocalDateTime());
        return billing;
    }

    public ObservableList<BillingBillingPayment> getBillingsWithPaymentInfo() throws SQLException {
        ObservableList<BillingBillingPayment> list = FXCollections.observableArrayList();
        String sql = "SELECT " +
                "billings.id, " +
                "billings.billing_no, " +
                "billings.account_no, " +
                "billings.from_date, " +
                "billings.to_date, " +
                "billings.due_date, " +
                "billings.to_pay, " +
                "billings.status, " +
                "billings.payment_no, " +
                "COALESCE(billing_payments.penalty, 0), " +
                "COALESCE(billing_payments.discount, 0), " +
                "COALESCE(billing_payments.amount_to_pay, 0), " +
                "COALESCE(billing_payments.amount_paid, 0), " +
                "COALESCE(billing_payments.balance, 0), " +
                "billing_payments.date_paid, " +
                "accounts.name FROM billings " +
                "LEFT OUTER JOIN billing_payments ON billing_payments.billing_id = billings.id " +
                "LEFT OUTER JOIN accounts ON accounts.account_no = billings.account_no " +
                "WHERE billings.date_deleted IS NULL";
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchBillingWithPaymentInfo(rs));
        }
        return list;
    }

    private BillingBillingPayment fetchBillingWithPaymentInfo(ResultSet rs) throws SQLException {
        BillingBillingPayment b = new BillingBillingPayment();
        b.setId(rs.getInt(1));
        b.setBillingNo(rs.getString(2));
        b.setAccountNo(rs.getString(3));
        b.setFromDate(rs.getDate(4).toLocalDate());
        b.setToDate(rs.getDate(5).toLocalDate());
        b.setDueDate(rs.getDate(6).toLocalDate());
        b.setToPay(rs.getDouble(7));
        b.setStatus(rs.getString(8));
        b.setPaymentNo(rs.getString(9));

        b.setPenalty(rs.getDouble(10));
        b.setDiscount(rs.getDouble(11));
        b.setAmountToPay(rs.getDouble(12));
        b.setAmountPaid(rs.getDouble(13));
        b.setBalance(rs.getDouble(14));
        Date datePaid = rs.getDate(15);
        if (datePaid != null) b.setDatePaid(datePaid.toLocalDate());
        b.setAccountName(rs.getString(16));
        return b;
    }
}
