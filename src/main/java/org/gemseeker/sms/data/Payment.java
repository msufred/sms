package org.gemseeker.sms.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Payment entry represents payment for billing, purchase or service. The extraInfo
 * column represents one of the following; billing_no, purchase_no, or service_no.
 */
public class Payment {

    public static final String TYPE_BILLING = "Billing";
    public static final String TYPE_PURCHASE = "Purchase";
    public static final String TYPE_SERVICE = "Service";

    private int id;
    private String paymentNo;       // OR No.
    private String name;            // payee name
    private String paymentFor;      // Billing, Purchase, Service
    private String extraInfo;       // billing_no, purchase_no, or service_no
    private double amountToPay;     // for billing -> monthly_fee + prev_balance
    private double discount;        // percent
    private double vat;
    private double surcharges;      // penalty & others
    private double amountTotal;     // amountToPay - discount + vat + surcharges
    private double amountPaid;
    private double balance;
    private LocalDate paymentDate;
    private String preparedBy;
    private String status;          // valid, void

    private String tag;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private LocalDateTime dateDeleted;

    public Payment() {
        paymentFor = TYPE_BILLING;
        amountToPay = discount = vat = amountTotal = amountPaid = balance = 0;
        status = "valid";
        tag = "normal";
        dateCreated = dateUpdated = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(String paymentFor) {
        this.paymentFor = paymentFor;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getSurcharges() {
        return surcharges;
    }

    public void setSurcharges(double surcharges) {
        this.surcharges = surcharges;
    }

    public double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
