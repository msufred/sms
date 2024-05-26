package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Account;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.models.AccountSubscription;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AccountController implements ModelController<Account> {

    private final Database database;

    public AccountController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Account model) throws SQLException {
        String sql = String.format("INSERT INTO accounts (account_no, name, address, phone, email, status, tag, " +
                "date_created, date_updated) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                model.getAccountNo(), model.getName(), model.getAddress(), model.getPhone(), model.getEmail(),
                model.getStatus(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Account model) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        String sql = String.format("UPDATE accounts SET name='%s', address='%s', phone='%s', email='%s', status='%s', " +
                "tag='%s', date_updated='%s' WHERE id='%d'", model.getName(), model.getAddress(), model.getPhone(),
                model.getEmail(), model.getStatus(), model.getTag(), now, model.getId());
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
        return update(id, "delete_deleted", LocalDateTime.now().toString());
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
        String sql = String.format("SELECT * FROM accounts WHERE account_no='%s' AND date_deleted IS NULL LIMIT 1", accoutNo);
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

    private Account fetchInfo(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt(1));
        account.setAccountNo(rs.getString(2));
        account.setName(rs.getString(3));
        account.setAddress(rs.getString(4));
        account.setPhone(rs.getString(5));
        account.setEmail(rs.getString(6));
        account.setStatus(rs.getString(7));
        account.setTag(rs.getString(8));
        account.setDateCreated(rs.getTimestamp(9).toLocalDateTime());
        account.setDateUpdated(rs.getTimestamp(10).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(11);
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
        AccountSubscription account = new AccountSubscription();
        account.setId(rs.getInt(1));
        account.setAccountNo(rs.getString(2));
        account.setName(rs.getString(3));
        account.setAddress(rs.getString(4));
        account.setPhone(rs.getString(5));
        account.setEmail(rs.getString(6));
        account.setStatus(rs.getString(7));
        account.setTag(rs.getString(8));
        account.setDateCreated(rs.getTimestamp(9).toLocalDateTime());
        account.setDateUpdated(rs.getTimestamp(10).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(11);
        if (dateDeleted != null) account.setDateDeleted(dateDeleted.toLocalDateTime());

        // sub
        account.setSubscriptionStatus(rs.getString(12));
        Date startDate = rs.getDate(13);
        if (startDate != null) account.setStartDate(startDate.toLocalDate());
        Date endDate = rs.getDate(14);
        if (endDate != null) account.setEndDate(endDate.toLocalDate());
        account.setMonthlyFee(rs.getDouble(15));

        account.setTowerName(rs.getString(16));
        account.setParentTower(rs.getString(17));

        return account;
    }
}
