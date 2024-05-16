package org.gemseeker.sms.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Expense {

    public static final String TYPE_ELECTRICITY = "Electricity";
    public static final String TYPE_WATER = "Water";
    public static final String TYPE_EDUCATION = "Education";
    public static final String TYPE_FOOD_GROCERY = "Food/Grocery";
    public static final String TYPE_TRANSPORTATION = "Transportation";
    public static final String TYPE_RENTAL = "Rental";
    public static final String TYPE_REPAIR = "Repair";
    public static final String TYPE_MAINTENANCE = "Maintenance";
    public static final String TYPE_PERIPHERALS = "Peripherals";
    public static final String TYPE_INTERNET = "Internet";
    public static final String TYPE_PAYMENT = "Payment";
    public static final String TYPE_OTHERS = "Others";

    private int id;
    private String type;
    private String description;
    private double amount;
    private LocalDate date;

    private String tag;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private LocalDateTime dateDeleted;

    public Expense() {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
