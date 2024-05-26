package org.gemseeker.sms.data;

import java.time.LocalDateTime;

public class PurchaseBilling {

    public static final String STATUS_FOR_PAYMENT = "For Payment";
    public static final String STATUS_PAID = "Paid";

    private int id;
    private String billingNo;
    private String name;
    private boolean walkInClient;
    private double toPay;
    private String status; // For Payment, Paid
    private String paymentNo; // if paid, not null

    private String tag;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private LocalDateTime dateDeleted;

    public PurchaseBilling() {
        toPay = 0.0;
        status = STATUS_FOR_PAYMENT;
        tag = "normal";
        dateCreated = dateUpdated = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillingNo() {
        return billingNo;
    }

    public void setBillingNo(String billingNo) {
        this.billingNo = billingNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWalkInClient() {
        return walkInClient;
    }

    public void setWalkInClient(boolean walkInClient) {
        this.walkInClient = walkInClient;
    }

    public double getToPay() {
        return toPay;
    }

    public void setToPay(double toPay) {
        this.toPay = toPay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
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
