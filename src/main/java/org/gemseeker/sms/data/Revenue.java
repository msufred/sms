package org.gemseeker.sms.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Revenue {

    public static final String TYPE_BILLING = "Billing";
    public static final String TYPE_PURCHASE = "Purchase";
    public static final String TYPE_SERVICE = "Service";
    public static final String TYPE_WIFI_VENDO = "WiFi Vendo";
    public static final String TYPE_OTHERS = "Others";

    public static final ObservableList<String> types = FXCollections.observableArrayList(
            TYPE_BILLING, TYPE_PURCHASE, TYPE_SERVICE, TYPE_WIFI_VENDO, TYPE_OTHERS
    );

    public static final String MODE_CASH = "Cash";
    public static final String MODE_GCASH = "GCash";
    public static final String MODE_BANK_CASH = "Bank Transfer (Cash)";
    public static final String MODE_BANK_CHEQUE = "Bank Transfer (Cheque)";
    public static final String MODE_PALAWAN_EXP = "Palawan Express";

    public static final ObservableList<String> modes = FXCollections.observableArrayList(
            MODE_CASH, MODE_GCASH, MODE_BANK_CASH, MODE_BANK_CHEQUE, MODE_PALAWAN_EXP
    );

    private int id;
    private String type;
    private String description;
    private double amount;
    private String mode; // cash, bank transfer etc 11/27/24
    private String reference;
    private LocalDate date;
    private String attachment;

    private String tag;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private LocalDateTime dateDeleted;

    public Revenue() {
        type = TYPE_OTHERS;
        description = "";
        amount = 0.0;
        tag = "normal";
        dateCreated = dateUpdated = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
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
