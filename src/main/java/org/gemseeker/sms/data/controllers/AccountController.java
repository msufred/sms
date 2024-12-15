package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Account;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.models.AccountSubscription;

import java.sql.*;
import java.time.LocalDateTime;

public class AccountController implements ModelController<Account> {

    private final Database database;

    public AccountController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Account model) throws SQLException {
        String sql = String.format("INSERT INTO accounts (account_no, name, address, phone, email, bank_account_name, " +
                        "bank_account_no, status, tag, date_created, date_updated) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                model.getAccountNo(), model.getName(), model.getAddress(), model.getPhone(), model.getEmail(), model.getBankAccountName(), model.getBankAccountNo(),
                model.getStatus(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Account model) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        String sql = String.format("UPDATE accounts SET account_no='%s', name='%s', address='%s', phone='%s', email='%s', " +
                        "bank_account_name='%s', bank_account_no='%s', status='%s', tag='%s', date_updated='%s' WHERE id='%d'",
                model.getAccountNo(), model.getName(), model.getAddress(), model.getPhone(), model.getEmail(),
                model.getBankAccountName(), model.getBankAccountNo(), model.getStatus(), model.getTag(), now, model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        String sql = String.format("UPDATE accounts SET %s='%s', date_updated='%s' WHERE id='%d'", col, value, now, id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    public boolean restore(int id) throws SQLException {
        return database.restore(id, "date_deleted", "accounts");
    }

    @Override
    public Account get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM accounts WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    public Account getByAccountNo(String accoutNo) throws SQLException {
        String sql = String.format("SELECT * FROM accounts WHERE account_no='%s' LIMIT 1", accoutNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<Account> getAll() throws SQLException {
        ObservableList<Account> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM accounts WHERE date_deleted IS NULL";
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    public ObservableList<Account> getAllActive() throws SQLException {
        ObservableList<Account> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM accounts WHERE status='Active' AND date_deleted IS NULL";
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while(rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    public ObservableList<Account> getDeleted() throws SQLException {
        ObservableList<Account> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM accounts WHERE date_deleted IS NOT NULL";
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Account fetchInfo(ResultSet rs) throws SQLException {
        int index = 1;
        Account account = new Account();
        account.setId(rs.getInt(index++));
        account.setAccountNo(rs.getString(index++));
        account.setName(rs.getString(index++));
        account.setAddress(rs.getString(index++));
        account.setPhone(rs.getString(index++));
        account.setEmail(rs.getString(index++));
        account.setBankAccountName(rs.getString(index++));
        account.setBankAccountNo(rs.getString(index++));
        account.setStatus(rs.getString(index++));
        account.setTag(rs.getString(index++));
        account.setDateCreated(rs.getTimestamp(index++).toLocalDateTime());
        account.setDateUpdated(rs.getTimestamp(index++).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(index);
        if (dateDeleted != null) account.setDateDeleted(dateDeleted.toLocalDateTime());
        return account;
    }

    public boolean hasAccount(String accountNo) throws SQLException {
        String sql = String.format("SELECT COUNT(*) FROM accounts WHERE account_no='%s'", accountNo);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }

    public ObservableList<AccountSubscription> getAccountsWithSubscription() throws SQLException {
        String sql = "SELECT accounts.*, subscriptions.status, subscriptions.start_date, subscriptions.end_date, " +
                "subscriptions.monthly_fee, towers.name, towers.parent_name FROM accounts " +
                "LEFT JOIN subscriptions ON subscriptions.account_no = accounts.account_no " +
                "LEFT JOIN towers ON towers.account_no = accounts.account_no " +
                "WHERE accounts.date_deleted IS NULL";
        ObservableList<AccountSubscription> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchAccountWithSubscriptionInfo(rs));
        }
        return list;
    }

    private AccountSubscription fetchAccountWithSubscriptionInfo(ResultSet rs) throws SQLException {
        int index = 1;
        AccountSubscription account = new AccountSubscription();
        account.setId(rs.getInt(index++));
        account.setAccountNo(rs.getString(index++));
        account.setName(rs.getString(index++));
        account.setAddress(rs.getString(index++));
        account.setPhone(rs.getString(index++));
        account.setEmail(rs.getString(index++));
        account.setBankAccountName(rs.getString(index++));
        account.setBankAccountNo(rs.getString(index++));
        account.setStatus(rs.getString(index++));
        account.setTag(rs.getString(index++));
        account.setDateCreated(rs.getTimestamp(index++).toLocalDateTime());
        account.setDateUpdated(rs.getTimestamp(index++).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(index++);
        if (dateDeleted != null) account.setDateDeleted(dateDeleted.toLocalDateTime());

        // sub
        account.setSubscriptionStatus(rs.getString(index++));
        Date startDate = rs.getDate(index++);
        if (startDate != null) account.setStartDate(startDate.toLocalDate());
        Date endDate = rs.getDate(index++);
        if (endDate != null) account.setEndDate(endDate.toLocalDate());
        account.setMonthlyFee(rs.getDouble(index++));

        account.setTowerName(rs.getString(index++));
        account.setParentTower(rs.getString(index));

        return account;
    }
}
