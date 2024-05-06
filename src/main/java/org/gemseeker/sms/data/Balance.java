package org.gemseeker.sms.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DEV NOTE: Balance table will act like a History table. Meaning, for each Account, there should be only ONE unpaid
 * balance (or Pending). Every time an Account makes a payment, if the previous Balance is not paid (or included in the
 * payment) AND that Account accumulate another balance, previous balances will be set to Paid status, and we will create
 * another Balance entry (calculating the previous balance plus the current balance).
 *
 * IF the Account paid a portion of the previous balance, we will also set the balance status to Paid, and we will create
 * new Balance entry for that remaining unpaid balance.
 *
 * IF all Balance is paid, then we will just set all Account's Balance entries status to Paid.
 */
public class Balance {

    private int id;
    private String accountNo;
    private double amount;
    private String status; // Paid, Pending
    private LocalDate datePaid;

    private String tag;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private LocalDateTime dateDeleted;

    public Balance() {
        amount = 0;
        status = "Pending";
        tag = "normal";
        dateCreated = dateUpdated = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(LocalDate datePaid) {
        this.datePaid = datePaid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDateTime getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(LocalDateTime dateDeleted) {
        this.dateDeleted = dateDeleted;
    }
}
