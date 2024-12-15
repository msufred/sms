package org.gemseeker.sms.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Expense {

    public static final String CAT_OPERATIONAL = "Operational";
    public static final String CAT_NONOPERATIONAL = "Non-Operational";

    public static final ObservableList<String> categories = FXCollections.observableArrayList(CAT_OPERATIONAL, CAT_NONOPERATIONAL);

    public static final String TYPE_ELECTRICITY = "Electricity";
    public static final String TYPE_WATER = "Water";
    public static final String TYPE_EDUCATION = "Education";
    public static final String TYPE_MEALS = "Meals";
    public static final String TYPE_TRANSPORTATION = "Transportation";
    public static final String TYPE_RENTAL = "Rental";
    public static final String TYPE_GROUND_RENTAL = "Ground Rental";
    public static final String TYPE_COMPUTER_PERIPHERALS = "Computer Peripherals";
    public static final String TYPE_WIFI_VENDO_ESSENTIALS = "WiFi Vendo Essentials";
    public static final String TYPE_BUSINESS_LICENSE_REGISTRATION = "Business License and Registration";
    public static final String TYPE_SALARY = "Salary";
    public static final String TYPE_REPAIR = "Repair";
    public static final String TYPE_MAINTENANCE = "Maintenance";
    public static final String TYPE_INTERNET = "Internet";
    public static final String TYPE_PAYMENT = "Payment";
    public static final String TYPE_BANK_LOAN = "Bank Loan";
    public static final String TYPE_PERSONAL_LOAN = "Personal Loan";
    public static final String TYPE_BUSINESS_LOAN = "Business Loan";
    public static final String TYPE_CAR_LOAN = "Car Loan";
    public static final String TYPE_SCHOOL_FEES = "School Fees";
    public static final String TYPE_APARTMENT_RENTAL = "Apartment Rental";
    public static final String TYPE_STUDENT_ALLOWANCE = "Student Allowance";
    public static final String TYPE_OTHERS = "Others";

    public static final ObservableList<String> operationalTypes = FXCollections.observableArrayList(
            TYPE_ELECTRICITY,
            TYPE_WATER,
            TYPE_MEALS,
            TYPE_TRANSPORTATION,
            TYPE_RENTAL,
            TYPE_GROUND_RENTAL,
            TYPE_COMPUTER_PERIPHERALS,
            TYPE_MAINTENANCE,
            TYPE_REPAIR,
            TYPE_INTERNET,
            TYPE_PAYMENT,
            TYPE_BUSINESS_LOAN,
            TYPE_WIFI_VENDO_ESSENTIALS,
            TYPE_BUSINESS_LICENSE_REGISTRATION,
            TYPE_SALARY
    );

    public static final ObservableList<String> nonOperationalTypes = FXCollections.observableArrayList(
            TYPE_ELECTRICITY,
            TYPE_WATER,
            TYPE_MEALS,
            TYPE_TRANSPORTATION,
            TYPE_RENTAL,
            TYPE_MAINTENANCE,
            TYPE_REPAIR,
            TYPE_INTERNET,
            TYPE_PAYMENT,
            TYPE_BANK_LOAN,
            TYPE_PERSONAL_LOAN,
            TYPE_CAR_LOAN,
            TYPE_EDUCATION,
            TYPE_SCHOOL_FEES,
            TYPE_APARTMENT_RENTAL,
            TYPE_STUDENT_ALLOWANCE,
            TYPE_OTHERS
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
    private String category;
    private String type;
    private String description;
    private double amount;
    private String mode;
    private String ref;
    private LocalDate date;
    private String attachment; // filename

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
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
