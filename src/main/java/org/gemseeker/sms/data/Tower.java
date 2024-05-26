package org.gemseeker.sms.data;

import java.time.LocalDateTime;

public class Tower {

    public static final String TYPE_SOURCE = "Source";
    public static final String TYPE_DEFAULT = "Default";
    public static final String TYPE_RELAY = "Relay";
    public static final String TYPE_ACCESS_POINT = "Access Point";

    private int id;
    private String accountNo;
    private String type;
    private String name;
    private float latitude;
    private float longitude;
    private float elevation;
    private double towerHeight;
    private String ipAddress;
    private int parentTowerId;
    private String parentName;
    private String status;

    private String tag;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private LocalDateTime dateDeleted;

    public Tower() {
        type = TYPE_DEFAULT;
        latitude = longitude = elevation = 0.0f;
        towerHeight = 0.0;
        parentTowerId = -1;
        status = "Active";
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getElevation() {
        return elevation;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    public double getTowerHeight() {
        return towerHeight;
    }

    public void setTowerHeight(double towerHeight) {
        this.towerHeight = towerHeight;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getParentTowerId() {
        return parentTowerId;
    }

    public void setParentTowerId(int parentTowerId) {
        this.parentTowerId = parentTowerId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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

    @Override
    public String toString() {
        return name;
    }
}
