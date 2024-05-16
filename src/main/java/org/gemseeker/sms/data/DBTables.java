package org.gemseeker.sms.data;

public final class DBTables {

    public static String[] createTableSql() {
        return new String[] {
                createUsersTable(),
                createDataPlansTable(),
                createWifiVendosTable(),
                createProductsTable(),
                createServicesTable(),
                createAccountsTable(),
                createSubscriptionsTable(),
                createTowersTable(),
                createBillingsTable(),
                createBillingStatementsTable(),
                createPaymentsTable(),
                createPaymentItemsTable(),
                createBalancesTable(),
                createExpensesTable(),
        };
    }

    public static String[] updatesSql() {
        return new String[] {
                updateTowersTable(),
        };
    }

    private static String createUsersTable() {
        return "CREATE TABLE IF NOT EXISTS users (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "username VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "role VARCHAR(8) DEFAULT 'guest', " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createDataPlansTable() {
        return "CREATE TABLE IF NOT EXISTS data_plans (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "speed INT DEFAULT '0', " +
                "monthly_fee DOUBLE DEFAULT '0.0', " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createWifiVendosTable() {
        return "CREATE TABLE IF NOT EXISTS wifi_vendos (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "ip_address VARCHAR(24), " +
                "status VARCHAR(16) DEFAULT 'Active', " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createProductsTable() {
        return "CREATE TABLE IF NOT EXISTS products (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "price DOUBLE DEFAULT '0.0', " +
                "stock INT DEFAULT '0', " +
                "serial VARCHAR(255), " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createServicesTable() {
        return "CREATE TABLE IF NOT EXISTS services (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "price DOUBLE DEFAULT '0.0', " +
                "description VARCHAR(255), " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createAccountsTable() {
        return "CREATE TABLE IF NOT EXISTS accounts (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "account_no VARCHAR(32) NOT NULL, " +
                "name VARCHAR(255) NOT NULL, " +
                "address VARCHAR(255), " +
                "phone VARCHAR(15), " +
                "email VARCHAR(255), " +
                "status VARCHAR(16) DEFAULT 'Active', " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createSubscriptionsTable() {
        return "CREATE TABLE IF NOT EXISTS subscriptions (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "account_no VARCHAR(32) NOT NULL, " +
                "plan_type VARCHAR(255) DEFAULT 'Standard', " +
                "speed INT DEFAULT '0', " +
                "monthly_fee DOUBLE DEFAULT '0.0', " +
                "ip_address VARCHAR(24), " +
                "start_date DATE NOT NULL, " +
                "end_date DATE NOT NULL, " +
                "status VARCHAR(16) DEFAULT 'Active', " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createTowersTable() {
        return "CREATE TABLE IF NOT EXISTS towers (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "account_no VARCHAR(32) NOT NULL, " +
                "type VARCHAR(24) DEFAULT 'Default', " +
                "latitude FLOAT DEFAULT '0.0', " +
                "longitude FLOAT DEFAULT '0.0', " +
                "elevation FLOAT DEFAULT '0.0', " +
                "tower_height DOUBLE DEFAULT '0.0', " +
                "ip_address VARCHAR(24), " +
                "parent_tower_id INT DEFAULT '-1', " +
                "status VARCHAR(16) DEFAULT 'Active', " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createBillingsTable() {
        return "CREATE TABLE IF NOT EXISTS billings (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "billing_no VARCHAR(10) NOT NULL, " +
                "account_no VARCHAR(32) NOT NULL, " +
                "from_date DATE NOT NULL, " +
                "to_date DATE NOT NULL, " +
                "due_date DATE NOT NULL, " +
                "to_pay DOUBLE DEFAULT '0.0', " +
                "status VARCHAR(16) DEFAULT 'For Payment', " +
                "payment_no VARCHAR(16), " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    /**
     * Table billing_statements holds the records of billing statements created & printed. I have to preserve the
     * data of that billing statement, so that I can print it anytime.
     */
    private static String createBillingStatementsTable() {
        return "CREATE TABLE IF NOT EXISTS billing_statements (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "billing_no VARCHAR(10) NOT NULL, " +
                "include_balance BOOLEAN DEFAULT 'false', " +
                "prev_balance DOUBLE DEFAULT '0.0', " +
                "monthly_fee DOUBLE DEFAULT '0.0', " +
                "discount DOUBLE DEFAULT '0.0', " +
                "penalty DOUBLE DEFAULT '0.0', " +
                "vat DOUBLE DEFAULT '0.0', " +
                "total DOUBLE DEFAULT '0.0', " +
                "prepared_by VARCHAR(255), " +
                "designation VARCHAR(255), " +
                "received_by VARCHAR(255), " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_printed TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createPaymentsTable() {
        return "CREATE TABLE IF NOT EXISTS payments (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "payment_no VARCHAR(10) NOT NULL, " +
                "name VARCHAR(255) NOT NULL, " +
                "payment_for VARCHAR(16) DEFAULT 'Billing', " +
                "extra_info VARCHAR(16), " +
                "prev_balance DOUBLE DEFAULT '0.0', " +
                "amount_to_pay DOUBLE DEFAULT '0.0', " +
                "discount DOUBLE DEFAULT '0.0', " +
                "vat DOUBLE DEFAULT '0.0', " +
                "surcharges DOUBLE DEFAULT '0.0', " +
                "amount_total DOUBLE DEFAULT '0.0', " +
                "amount_paid DOUBLE DEFAULT '0.0', " +
                "balance DOUBLE DEFAULT '0.0', " +
                "payment_date DATE NOT NULL, " +
                "prepared_by VARCHAR(255), " +
                "status VARCHAR(16) DEFAULT 'valid', " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createPaymentItemsTable() {
        return "CREATE TABLE IF NOT EXISTS payment_items (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "payment_no VARCHAR(10) NOT NULL, " +
                "item_no VARCHAR(10), " +
                "item_name VARCHAR(255), " +
                "serial VARCHAR(255), " +
                "amount DOUBLE DEFAULT '0.0', " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createBalancesTable() {
        return "CREATE TABLE IF NOT EXISTS balances (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "account_no VARCHAR(32) NOT NULL, " +
                "amount DOUBLE DEFAULT '0.0', " +
                "status VARCHAR(16) DEFAULT 'Pending', " +
                "date_paid DATE, " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    private static String createExpensesTable() {
        return "CREATE TABLE IF NOT EXISTS expenses (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "type VARCHAR(255) DEFAULT 'Others', " +
                "description VARCHAR(255) DEFAULT '', " +
                "amount DOUBLE DEFAULT '0.0', " +
                "date DATE NOT NULL, " +
                "tag VARCHAR(16) DEFAULT 'normal', " +
                "date_created TIMESTAMP, " +
                "date_updated TIMESTAMP, " +
                "date_deleted TIMESTAMP, " +
                "PRIMARY KEY (id)" +
                ")";
    }

    // Updates

    private static String updateTowersTable() {
        return "ALTER TABLE towers ADD COLUMN IF NOT EXISTS name VARCHAR(255) DEFAULT '' AFTER type";
    }
}
